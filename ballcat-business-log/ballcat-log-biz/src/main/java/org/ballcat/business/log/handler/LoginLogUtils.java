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

package org.ballcat.business.log.handler;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.ballcat.business.log.model.entity.LoginLog;
import org.ballcat.common.core.constant.MDCConstants;
import org.ballcat.common.core.constant.enums.BooleanEnum;
import org.ballcat.common.util.IpUtils;
import org.ballcat.common.util.UserAgentUtils;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
		HttpServletRequest request = ((ServletRequestAttributes) Objects
			.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		return new LoginLog().setLoginTime(LocalDateTime.now())
			.setIp(IpUtils.getIpAddr(request))
			.setOs(detectOS(request))
			.setBrowser(detectBrowser(request))
			.setStatus(BooleanEnum.TRUE.intValue()) // TODO 使用登录日志自定义的枚举
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
