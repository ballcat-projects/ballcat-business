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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 消息处理代理
 *
 * @param <T> 消息类型
 * @author huyuanzhi
 */
@Slf4j
@Component
@AllArgsConstructor
public class NotifyInfoDelegateHandler<T extends NotifyInfo> {

	private final List<NotifyInfoHandler<T>> notifyInfoHandlers;

	private Map<Class<?>, NotifyInfoHandler<T>> handlerMap;

	@PostConstruct
	public void init() {
		this.handlerMap = new HashMap<>(this.notifyInfoHandlers.size());
		for (NotifyInfoHandler<T> handler : this.notifyInfoHandlers) {
			this.handlerMap.put(handler.getNotifyClass(), handler);
		}
	}

	/**
	 * 代理方法
	 * @param userList 发送用户列表
	 * @param info 消息
	 */
	public void handle(List<SysUser> userList, T info) {
		Assert.notNull(info, "event message cant be null!");
		NotifyInfoHandler<T> notifyInfoHandler = this.handlerMap.get(info.getClass());
		if (notifyInfoHandler == null) {
			log.warn("no notifyHandler bean for class:{},please check!", info.getClass().getName());
			return;
		}
		notifyInfoHandler.handle(userList, info);
	}

}
