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

package org.ballcat.business.notify.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通知接收者筛选类型
 *
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum NotifyRecipientFilterTypeEnum {

	// 全部
	ALL(1),
	// 指定用户
	SPECIFY_ROLE(2),
	// 指定组织
	SPECIFY_ORGANIZATION(3),
	// 指定用户类型
	SPECIFY_USER_TYPE(4),
	// 指定用户
	SPECIFY_USER(5),;

	private final int value;

}
