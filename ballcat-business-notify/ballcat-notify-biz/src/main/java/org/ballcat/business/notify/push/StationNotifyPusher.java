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

package org.ballcat.business.notify.push;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.ballcat.business.notify.enums.NotifyChannelEnum;
import org.ballcat.business.notify.event.StationNotifyPushEvent;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 消息通知站内推送
 *
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class StationNotifyPusher implements NotifyPusher {

	private final ApplicationEventPublisher publisher;

	/**
	 * 当前发布者对应的接收方式
	 * @return 推送方式
	 * @see NotifyChannelEnum
	 */
	@Override
	public Integer notifyChannel() {
		return NotifyChannelEnum.STATION.getValue();
	}

	@Override
	public void push(NotifyInfo notifyInfo, List<SysUser> userList) {
		// 发布事件，监听者进行实际的 websocket 推送
		this.publisher.publishEvent(new StationNotifyPushEvent(notifyInfo, userList));
	}

}
