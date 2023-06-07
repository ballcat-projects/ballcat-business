package org.ballcat.admin.websocket;

import org.ballcat.admin.websocket.listener.NotifyWebsocketEventListener;
import org.ballcat.websocket.distribute.MessageDistributor;
import org.ballcat.business.notify.handler.NotifyInfoDelegateHandler;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.notify.service.UserAnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ConditionalOnClass({ NotifyWebsocketEventListener.class, UserAnnouncementService.class })
@Configuration(proxyBeanMethods = false)
public class NotifyWebsocketEventListenerConfiguration {

	private final MessageDistributor messageDistributor;

	@Bean
	public NotifyWebsocketEventListener notifyWebsocketEventListener(
			NotifyInfoDelegateHandler<? super NotifyInfo> notifyInfoDelegateHandler) {
		return new NotifyWebsocketEventListener(messageDistributor, notifyInfoDelegateHandler);
	}

}
