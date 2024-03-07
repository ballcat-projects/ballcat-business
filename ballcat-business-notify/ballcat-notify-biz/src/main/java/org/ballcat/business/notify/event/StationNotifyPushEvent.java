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

package org.ballcat.business.notify.event;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;

/**
 * @author Hccake 2021/1/5
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public class StationNotifyPushEvent {

	/**
	 * 通知信息
	 */
	private final NotifyInfo notifyInfo;

	/**
	 * 推送用户列表
	 */
	private final List<SysUser> userList;

}
