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

package org.ballcat.admin.upms.log;

import lombok.RequiredArgsConstructor;
import org.ballcat.business.log.enums.LoginEventTypeEnum;
import org.ballcat.business.log.handler.LoginLogUtils;
import org.ballcat.business.log.model.entity.LoginLog;
import org.ballcat.business.log.service.LoginLogService;
import org.ballcat.common.core.constant.enums.BooleanEnum;
import org.ballcat.springsecurity.oauth2.server.authorization.authentication.OAuth2TokenRevocationAuthenticationToken;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * spring 授权服务器的登录日志处理器
 *
 * @author hccake
 */
@RequiredArgsConstructor
public class SpringAuthorizationServerLoginLogHandler implements LoginLogHandler {

	private final LoginLogService loginLogService;

	private final AuthorizationServerSettings authorizationServerSettings;

	/**
	 * 登录成功事件监听 记录用户登录日志
	 * @param event 登录成功 event
	 */
	@EventListener(AuthenticationSuccessEvent.class)
	public void onAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
		Object source = event.getSource();
		String username = null;

		String tokenEndpoint = this.authorizationServerSettings.getTokenEndpoint();
		boolean isOauth2LoginRequest = isOauth2LoginRequest(tokenEndpoint);

		// Oauth2登录 和表单登录 处理分开
		if (isOauth2LoginRequest && source instanceof OAuth2AccessTokenAuthenticationToken) {
			username = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		else if (!isOauth2LoginRequest && source instanceof UsernamePasswordAuthenticationToken) {
			username = ((UsernamePasswordAuthenticationToken) source).getName();
		}

		if (username != null) {
			LoginLog loginLog = LoginLogUtils.prodLoginLog(username)
				.setMsg("登录成功")
				.setStatus(BooleanEnum.TRUE.intValue()) // TODO 使用登录日志自定义枚举
				.setEventType(LoginEventTypeEnum.LOGIN.getValue());
			this.loginLogService.save(loginLog);
		}
	}

	/**
	 * 监听鉴权失败事件，记录登录失败日志
	 * @param event the event
	 */
	@EventListener(AbstractAuthenticationFailureEvent.class)
	public void onAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
		if (event.getException().getClass().isAssignableFrom(ProviderNotFoundException.class)) {
			return;
		}

		Object source = event.getSource();
		String username = null;

		String tokenEndpoint = this.authorizationServerSettings.getTokenEndpoint();
		boolean isOauth2LoginRequest = isOauth2LoginRequest(tokenEndpoint);

		// Oauth2登录 和表单登录 处理分开
		if (isOauth2LoginRequest && source instanceof OAuth2AuthorizationGrantAuthenticationToken) {
			username = ((OAuth2AuthorizationGrantAuthenticationToken) source).getName();
		}
		else if (!isOauth2LoginRequest && source instanceof UsernamePasswordAuthenticationToken) {
			username = ((UsernamePasswordAuthenticationToken) source).getName();
		}

		if (username != null) {
			LoginLog loginLog = LoginLogUtils.prodLoginLog(username)
				.setMsg(event.getException().getMessage())
				.setEventType(LoginEventTypeEnum.LOGIN.getValue())
				.setStatus(BooleanEnum.FALSE.intValue()); // TODO 使用登录日志自定义枚举
			this.loginLogService.save(loginLog);
		}
	}

	/**
	 * 登出成功事件监听
	 * @param event the event
	 */
	@EventListener(LogoutSuccessEvent.class)
	public void onLogoutSuccessEvent(LogoutSuccessEvent event) {
		Object source = event.getSource();
		String username = null;

		String tokenRevocationEndpoint = this.authorizationServerSettings.getTokenRevocationEndpoint();
		boolean isOauth2Login = isOauth2LoginRequest(tokenRevocationEndpoint);

		// Oauth2撤销令牌 和表单登出 处理分开
		if (isOauth2Login && source instanceof OAuth2TokenRevocationAuthenticationToken) {
			OAuth2Authorization authorization = ((OAuth2TokenRevocationAuthenticationToken) source).getAuthorization();
			username = authorization.getPrincipalName();
		}
		else if (!isOauth2Login && source instanceof UsernamePasswordAuthenticationToken) {
			username = ((UsernamePasswordAuthenticationToken) source).getName();
		}

		if (username != null) {
			LoginLog loginLog = LoginLogUtils.prodLoginLog(username)
				.setMsg("登出成功")
				.setEventType(LoginEventTypeEnum.LOGOUT.getValue());
			this.loginLogService.save(loginLog);
		}
	}

	private boolean isOauth2LoginRequest(String endpoint) {
		boolean isOauth2LoginRequest = false;
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
			isOauth2LoginRequest = servletRequestAttributes.getRequest().getRequestURI().equals(endpoint);
		}
		return isOauth2LoginRequest;
	}

}
