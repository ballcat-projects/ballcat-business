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

package org.ballcat.business.notify.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.common.util.JsonUtils;
import org.ballcat.websocket.distribute.MessageDO;
import org.ballcat.websocket.distribute.MessageDistributor;
import org.ballcat.websocket.message.JsonWebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * 公告通知
 *
 * @param <T>event消息对象
 * @param <M> websocket发送消息对象
 * @author huyuanzhi
 */
public abstract class AbstractNotifyInfoHandler<T extends NotifyInfo, M extends JsonWebSocketMessage>
		implements NotifyInfoHandler<T> {

	@Autowired
	private MessageDistributor messageDistributor;

	protected final Class<T> clz;

	@SuppressWarnings("unchecked")
	protected AbstractNotifyInfoHandler() {
		Type superClass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superClass;
		this.clz = (Class<T>) type.getActualTypeArguments()[0];
	}

	@Override
	public void handle(List<SysUser> userList, T notifyInfo) {
		M message = createMessage(notifyInfo);
		String msg = JsonUtils.toJson(message);
		List<Object> sessionKeys = userList.stream().map(SysUser::getUserId).collect(Collectors.toList());
		persistMessage(userList, notifyInfo);
		MessageDO messageDO = new MessageDO().setMessageText(msg)
			.setSessionKeys(sessionKeys)
			.setNeedBroadcast(CollectionUtils.isEmpty(sessionKeys));
		this.messageDistributor.distribute(messageDO);
	}

	@Override
	public Class<T> getNotifyClass() {
		return this.clz;
	}

	/**
	 * 持久化通知
	 * @param userList 通知用户列表
	 * @param notifyInfo 消息内容
	 */
	protected abstract void persistMessage(List<SysUser> userList, T notifyInfo);

	/**
	 * 产生推送消息
	 * @param notifyInfo 消息内容
	 * @return 分发消息
	 */
	protected abstract M createMessage(T notifyInfo);

}
