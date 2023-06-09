package org.ballcat.business.log.handler;

import org.ballcat.business.log.model.entity.LoginLog;
import org.ballcat.common.core.constant.MDCConstants;
import org.ballcat.common.core.util.WebUtils;
import org.ballcat.common.util.IpUtils;
import org.ballcat.common.util.UserAgentUtils;
import org.ballcat.log.operation.enums.LogStatusEnum;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author hccake
 */
public final class LoginLogUtils {

	private LoginLogUtils() {
	}

	/**
	 * 根据token和请求信息产生一个登录日志
	 * @param username 用户名
	 * @return LoginLog 登录日志
	 */
	public static LoginLog prodLoginLog(String username) {
		// 获取 Request
		HttpServletRequest request = WebUtils.getRequest();
		return new LoginLog().setLoginTime(LocalDateTime.now())
			.setIp(IpUtils.getIpAddr(request))
			.setOs(detectOS(request))
			.setBrowser(detectBrowser(request))
			.setStatus(LogStatusEnum.SUCCESS.getValue())
			.setTraceId(MDC.get(MDCConstants.TRACE_ID_KEY))
			.setUsername(username);
	}

	/**
	 * 获取浏览器 现代浏览器基本支持Client Hints，Firefox IE11等不支持的走UserAgentUtils.detect判断
	 */
	private static String detectBrowser(HttpServletRequest request) {
		String secChUa = request.getHeader("sec-ch-ua");
		String ua = request.getHeader("user-agent");
		if (secChUa != null) {
			// 剔除Brand和多余符号
			return secChUa.replaceAll("(,\\s+)?\\S+/Brand\\S+\\d+\"(,\\s+)?", "")
				.replaceAll("\"", "")
				.replaceAll(";v=", " ");
		}
		else {
			try {
				return UserAgentUtils.detectBrowser(ua);
			}
			catch (Exception e) {
				// 确保即便解析失败也不会报错
				return null;
			}
		}
	}

	/**
	 * 获取OS
	 */
	private static String detectOS(HttpServletRequest request) {
		String secChUaPlatform = request.getHeader("sec-ch-ua-platform");
		String ua = request.getHeader("user-agent");
		if (secChUaPlatform != null) {
			return secChUaPlatform.replaceAll("\"", "");
		}
		else {
			try {
				return UserAgentUtils.detectOS(ua);
			}
			catch (Exception e) {
				// 确保即便解析失败也不会报错
				return null;
			}
		}
	}

}
