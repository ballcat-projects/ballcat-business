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

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.admin.websocket.message.AnnouncementCloseMessage;
import org.ballcat.business.notify.event.AnnouncementCloseEvent;
import org.ballcat.business.notify.event.StationNotifyPushEvent;
import org.ballcat.business.notify.handler.NotifyInfoDelegateHandler;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.common.util.JsonUtils;
import org.ballcat.websocket.distribute.MessageDO;
import org.ballcat.websocket.distribute.MessageDistributor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * @author Hccake 2021/1/5
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class NotifyWebsocketEventListener {

	private final MessageDistributor messageDistributor;

	private final NotifyInfoDelegateHandler<? super NotifyInfo> notifyInfoDelegateHandler;

	/**
	 * 公告关闭事件监听
	 * @param event the AnnouncementCloseEvent
	 */
	@Async
	@EventListener(AnnouncementCloseEvent.class)
	public void onAnnouncementCloseEvent(AnnouncementCloseEvent event) {
		// 构建公告关闭的消息体
		AnnouncementCloseMessage message = new AnnouncementCloseMessage();
		message.setId(event.getId());
		String msg = JsonUtils.toJson(message);

		// 广播公告关闭信息
		MessageDO messageDO = new MessageDO().setMessageText(msg).setNeedBroadcast(true);
		this.messageDistributor.distribute(messageDO);
	}

	/**
	 * 站内通知推送事件
	 * @param event the StationNotifyPushEvent
	 */
	@Async
	@EventListener(StationNotifyPushEvent.class)
	public void onAnnouncementPublishEvent(StationNotifyPushEvent event) {
		NotifyInfo notifyInfo = event.getNotifyInfo();
		List<SysUser> userList = event.getUserList();
		this.notifyInfoDelegateHandler.handle(userList, notifyInfo);
	}

}
