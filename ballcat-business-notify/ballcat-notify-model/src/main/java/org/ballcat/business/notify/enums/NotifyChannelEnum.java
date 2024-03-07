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
 * 通知接收方式
 *
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum NotifyChannelEnum {

	// 站内
	STATION(1),
	// 短信
	SMS(2),
	// 邮件
	MAIL(3);

	private final int value;

}
