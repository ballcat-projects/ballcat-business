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

package org.ballcat.business.system.event;

import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.ballcat.business.system.model.entity.SysUser;

/**
 * 用户创建事件
 *
 * @author Yakir
 */
@Getter
@ToString
public class UserCreatedEvent {

	private final SysUser sysUser;

	private final List<String> roleCodes;

	public UserCreatedEvent(SysUser sysUser, List<String> roleCodes) {
		this.sysUser = sysUser;
		this.roleCodes = roleCodes;
	}

}
