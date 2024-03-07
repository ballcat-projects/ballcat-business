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

package org.ballcat.admin.websocket.listener;

import lombok.RequiredArgsConstructor;
import org.ballcat.admin.websocket.message.DictChangeMessage;
import org.ballcat.business.infra.event.DictChangeEvent;
import org.ballcat.common.util.JsonUtils;
import org.ballcat.websocket.distribute.MessageDO;
import org.ballcat.websocket.distribute.MessageDistributor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public class SystemWebsocketEventListener {

	private final MessageDistributor messageDistributor;

	/**
	 * 字典修改事件监听
	 * @param event the `DictChangeEvent`
	 */
	@Async
	@EventListener(DictChangeEvent.class)
	public void onDictChangeEvent(DictChangeEvent event) {
		// 构建字典修改的消息体
		DictChangeMessage dictChangeMessage = new DictChangeMessage();
		dictChangeMessage.setDictCode(event.getDictCode());
		String msg = JsonUtils.toJson(dictChangeMessage);

		// 广播修改信息
		MessageDO messageDO = new MessageDO().setMessageText(msg).setNeedBroadcast(true);
		this.messageDistributor.distribute(messageDO);
	}

}
