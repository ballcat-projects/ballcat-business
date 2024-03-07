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

package org.ballcat.business.notify.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.notify.event.NotifyPublishEvent;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.notify.push.NotifyPushExecutor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 通知发布事件监听器
 *
 * @author Hccake 2020/12/17
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyPublishEventListener {

	private final NotifyPushExecutor notifyPushExecutor;

	/**
	 * 通知发布事件
	 * @param event the NotifyPublishEvent
	 */
	@Async
	@EventListener(NotifyPublishEvent.class)
	public void onNotifyPublishEvent(NotifyPublishEvent event) {
		NotifyInfo notifyInfo = event.getNotifyInfo();
		// 推送通知
		this.notifyPushExecutor.push(notifyInfo);
	}

}
