package org.ballcat.admin.websocket;

import org.ballcat.admin.websocket.component.UserAttributeHandshakeInterceptor;
import org.ballcat.admin.websocket.component.UserSessionKeyGenerator;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.ballcat.websocket.session.SessionKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @author Hccake 2021/1/5
 * @version 1.0
 */
@Import({ SystemWebsocketEventListenerConfiguration.class, NotifyWebsocketEventListenerConfiguration.class })
@Configuration
@RequiredArgsConstructor
public class AdminWebSocketAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(UserAttributeHandshakeInterceptor.class)
	public HandshakeInterceptor authenticationHandshakeInterceptor(
			PrincipalAttributeAccessor principalAttributeAccessor) {
		return new UserAttributeHandshakeInterceptor(principalAttributeAccessor);
	}

	@Bean
	@ConditionalOnMissingBean(SessionKeyGenerator.class)
	public SessionKeyGenerator userSessionKeyGenerator() {
		return new UserSessionKeyGenerator();
	}

}
