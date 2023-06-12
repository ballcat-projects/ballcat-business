package org.ballcat.business.log.handler;

import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.log.model.entity.AccessLog;
import org.ballcat.business.log.thread.AccessLogSaveThread;
import org.ballcat.common.core.constant.MDCConstants;
import org.ballcat.common.util.IpUtils;
import org.ballcat.common.util.JsonUtils;
import org.ballcat.desensitize.DesensitizationHandlerHolder;
import org.ballcat.desensitize.enums.RegexDesensitizationTypeEnum;
import org.ballcat.log.access.handler.AccessLogHandler;
import org.ballcat.log.util.LogUtils;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 访问日志
 *
 * @author hccake 2019-10-16 16:09:25
 */
@Slf4j
public class CustomAccessLogHandler implements AccessLogHandler<AccessLog> {

	private static final String APPLICATION_JSON = "application/json";

	private final AccessLogSaveThread accessLogSaveThread;

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	public CustomAccessLogHandler(AccessLogSaveThread accessLogSaveThread,
			PrincipalAttributeAccessor principalAttributeAccessor) {
		if (!accessLogSaveThread.isAlive()) {
			accessLogSaveThread.start();
		}
		this.accessLogSaveThread = accessLogSaveThread;
		this.principalAttributeAccessor = principalAttributeAccessor;
	}

	/**
	 * 需要脱敏记录的参数
	 */
	private final List<String> needDesensitizeParams = Arrays.asList("password", "pass", "passConfirm");

	/**
	 * 生产一个日志
	 * @return accessLog
	 * @param request 请求信息
	 * @param response 响应信息
	 * @param time 执行时长
	 * @param myThrowable 异常信息
	 */
	@Override
	public AccessLog buildLog(HttpServletRequest request, HttpServletResponse response, Long time,
			Throwable myThrowable) {
		Object matchingPatternAttr = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		String matchingPattern = matchingPatternAttr == null ? "" : String.valueOf(matchingPatternAttr);
		// @formatter:off
		String uri = request.getRequestURI();
		AccessLog accessLog = new AccessLog()
				.setTraceId(MDC.get(MDCConstants.TRACE_ID_KEY))
				.setCreateTime(LocalDateTime.now())
				.setTime(time)
				.setIp(IpUtils.getIpAddr(request))
				.setMethod(request.getMethod())
				.setUserAgent(request.getHeader("user-agent"))
				.setUri(uri)
				.setMatchingPattern(matchingPattern)
				.setErrorMsg(Optional.ofNullable(myThrowable).map(Throwable::getMessage).orElse(""))
				.setHttpStatus(response.getStatus());
		// @formatter:on

		// 参数获取
		String params = getParams(request);
		accessLog.setReqParams(params);

		// 记录请求体
		if (shouldRecordRequestBody(request, uri)) {
			accessLog.setReqBody(LogUtils.getRequestBody(request));
		}

		// 只记录响应体
		if (shouldRecordResponseBody(response, uri)) {
			accessLog.setResult(LogUtils.getResponseBody(request, response));
		}

		// 如果登录用户 则记录用户名和用户id
		accessLog.setUserId(principalAttributeAccessor.getUserId());
		accessLog.setUsername(principalAttributeAccessor.getName());

		return accessLog;
	}

	/**
	 * 是否应该记录请求体
	 * @param request 请求信息
	 * @param uri 当前请求的uri
	 * @return 记录返回 true，否则返回 false
	 */
	protected boolean shouldRecordRequestBody(HttpServletRequest request, String uri) {
		// TODO 使用注解控制此次请求是否记录body，更方便个性化定制
		// 文件上传请求、用户改密时、验证码请求不记录body
		return !LogUtils.isMultipartContent(request) && !uri.matches("^/system/user/pass/[^/]+/?$")
				&& !uri.matches("^/captcha/.*$");
	}

	/**
	 * 是否应该记录响应体
	 * @param response 响应信息
	 * @param uri 当前请求的uri
	 * @return 记录返回 true，否则返回 false
	 */
	protected boolean shouldRecordResponseBody(HttpServletResponse response, String uri) {
		// 只对 content-type 为 application/json 的响应记录响应体（分页请求除外）
		return !uri.endsWith("/page") && response.getContentType() != null
				&& response.getContentType().contains(APPLICATION_JSON);
	}

	/**
	 * 获取参数信息
	 * @param request 请求信息
	 * @return 请求参数
	 */
	public String getParams(HttpServletRequest request) {
		String params;
		try {
			Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
			for (String paramKey : needDesensitizeParams) {
				String[] values = parameterMap.get(paramKey);
				if (values != null && values.length != 0) {
					String value = DesensitizationHandlerHolder.getRegexDesensitizationHandler()
						.handle(values[0], RegexDesensitizationTypeEnum.ENCRYPTED_PASSWORD);
					parameterMap.put(paramKey, new String[] { value });
				}
			}
			params = JsonUtils.toJson(parameterMap);
		}
		catch (Exception e) {
			params = "记录参数异常";
			log.error("[prodLog]，参数获取序列化异常", e);
		}
		return params;
	}

	/**
	 * 记录日志
	 * @param accessLog 访问日志
	 */
	@Override
	public void saveLog(AccessLog accessLog) {
		accessLogSaveThread.put(accessLog);
	}

}
