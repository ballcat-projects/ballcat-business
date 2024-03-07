/*
 * Copyright 2023-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ballcat.business.log.filter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.log.model.entity.AccessLog;
import org.ballcat.business.log.thread.AccessLogSaveThread;
import org.ballcat.common.core.constant.MDCConstants;
import org.ballcat.common.util.IpUtils;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.ballcat.web.accesslog.AccessLogRecordOptions;
import org.ballcat.web.accesslog.AccessLogRule;
import org.ballcat.web.accesslog.DefaultAccessLogFilter;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 业务访问日志，落库存储
 *
 * @author hccake 2019-10-16 16:09:25
 */
@Slf4j
public class BusinessAccessLogFilter extends DefaultAccessLogFilter {

	private final AccessLogSaveThread accessLogSaveThread;

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	public BusinessAccessLogFilter(AccessLogRecordOptions defaultRecordOptions, List<AccessLogRule> logRules,
			AccessLogSaveThread accessLogSaveThread, PrincipalAttributeAccessor principalAttributeAccessor) {
		super(defaultRecordOptions, logRules);
		if (!accessLogSaveThread.isAlive()) {
			accessLogSaveThread.start();
		}
		this.accessLogSaveThread = accessLogSaveThread;
		this.principalAttributeAccessor = principalAttributeAccessor;
	}

	@Override
	protected boolean shouldLog(HttpServletRequest request) {
		return true;
	}

	@Override
	protected void afterRequest(HttpServletRequest request, HttpServletResponse response, Long executionTime,
			Throwable throwable, AccessLogRecordOptions recordOptions) {
		// 默认不记录 options 请求
		if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
			return;
		}

		Object matchingPatternAttr = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		String matchingPattern = matchingPatternAttr == null ? "" : String.valueOf(matchingPatternAttr);
		// @formatter:off
		String uri = request.getRequestURI();
		AccessLog accessLog = new AccessLog()
				.setTraceId(MDC.get(MDCConstants.TRACE_ID_KEY))
				.setCreateTime(LocalDateTime.now())
				.setExecutionTime(executionTime)
				.setClientIp(IpUtils.getIpAddr(request))
				.setRequestMethod(request.getMethod())
				.setUserAgent(request.getHeader("user-agent"))
				.setRequestUri(uri)
				.setMatchingPattern(matchingPattern)
				.setErrorMessage(Optional.ofNullable(throwable).map(Throwable::getMessage).orElse(""))
				.setResponseStatus(response.getStatus());
		// @formatter:on

		// 记录查询参数
		if (recordOptions.isIncludeQueryString()) {
			accessLog.setQueryString(request.getQueryString());
		}

		// 记录请求体
		if (recordOptions.isIncludeRequestBody()) {
			accessLog.setRequestBody(getRequestBody(request));
		}

		// 记录响应体
		if (recordOptions.isIncludeResponseBody()) {
			accessLog.setResponseBody(getResponseBody(response));
		}

		// 如果登录用户 则记录用户名和用户id
		accessLog.setUserId(this.principalAttributeAccessor.getUserId());
		accessLog.setUsername(this.principalAttributeAccessor.getUsername());

		this.accessLogSaveThread.put(accessLog);
	}

}
