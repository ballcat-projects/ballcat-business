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

package org.ballcat.admin.websocket;

import lombok.RequiredArgsConstructor;
import org.ballcat.admin.websocket.listener.NotifyWebsocketEventListener;
import org.ballcat.business.notify.handler.NotifyInfoDelegateHandler;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.notify.service.UserAnnouncementService;
import org.ballcat.websocket.distribute.MessageDistributor;
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
		return new NotifyWebsocketEventListener(this.messageDistributor, notifyInfoDelegateHandler);
	}

}
