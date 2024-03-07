package org.ballcat.admin.upms.log;

import lombok.RequiredArgsConstructor;
import org.ballcat.business.log.enums.LoginEventTypeEnum;
import org.ballcat.business.log.model.entity.LoginLog;
import org.ballcat.business.log.service.LoginLogService;
import org.ballcat.log.operation.enums.LogStatusEnum;
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

import static org.ballcat.business.log.handler.LoginLogUtils.prodLoginLog;

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

		String tokenEndpoint = authorizationServerSettings.getTokenEndpoint();
		boolean isOauth2LoginRequest = isOauth2LoginRequest(tokenEndpoint);

		// Oauth2登录 和表单登录 处理分开
		if (isOauth2LoginRequest && source instanceof OAuth2AccessTokenAuthenticationToken) {
			username = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		else if (!isOauth2LoginRequest && source instanceof UsernamePasswordAuthenticationToken) {
			username = ((UsernamePasswordAuthenticationToken) source).getName();
		}

		if (username != null) {
			LoginLog loginLog = prodLoginLog(username).setMsg("登录成功")
				.setStatus(LogStatusEnum.SUCCESS.getValue())
				.setEventType(LoginEventTypeEnum.LOGIN.getValue());
			loginLogService.save(loginLog);
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

		String tokenEndpoint = authorizationServerSettings.getTokenEndpoint();
		boolean isOauth2LoginRequest = isOauth2LoginRequest(tokenEndpoint);

		// Oauth2登录 和表单登录 处理分开
		if (isOauth2LoginRequest && source instanceof OAuth2AuthorizationGrantAuthenticationToken) {
			username = ((OAuth2AuthorizationGrantAuthenticationToken) source).getName();
		}
		else if (!isOauth2LoginRequest && source instanceof UsernamePasswordAuthenticationToken) {
			username = ((UsernamePasswordAuthenticationToken) source).getName();
		}

		if (username != null) {
			LoginLog loginLog = prodLoginLog(username).setMsg(event.getException().getMessage())
				.setEventType(LoginEventTypeEnum.LOGIN.getValue())
				.setStatus(LogStatusEnum.FAIL.getValue());
			loginLogService.save(loginLog);
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

		String tokenRevocationEndpoint = authorizationServerSettings.getTokenRevocationEndpoint();
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
			LoginLog loginLog = prodLoginLog(username).setMsg("登出成功")
				.setEventType(LoginEventTypeEnum.LOGOUT.getValue());
			loginLogService.save(loginLog);
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
