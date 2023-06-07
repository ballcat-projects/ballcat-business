package org.ballcat.admin.websocket;

import org.ballcat.admin.websocket.listener.SystemWebsocketEventListener;
import org.ballcat.websocket.distribute.MessageDistributor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hccake
 */
@RequiredArgsConstructor
@ConditionalOnClass(SystemWebsocketEventListener.class)
@Configuration(proxyBeanMethods = false)
public class SystemWebsocketEventListenerConfiguration {

	private final MessageDistributor messageDistributor;

	@Bean
	public SystemWebsocketEventListener systemWebsocketEventListener() {
		return new SystemWebsocketEventListener(messageDistributor);
	}

}
