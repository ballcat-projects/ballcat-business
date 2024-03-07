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

import java.util.List;

import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;

/**
 * event消息处理接口
 *
 * @author huyuanzhi
 */
public interface NotifyInfoHandler<T extends NotifyInfo> {

	/**
	 * 处理消息
	 * @param userList 发送用户列表
	 * @param info 消息
	 */
	void handle(List<SysUser> userList, T info);

	/**
	 * 获取消息类型
	 * @return 消息类型
	 */
	Class<T> getNotifyClass();

}
