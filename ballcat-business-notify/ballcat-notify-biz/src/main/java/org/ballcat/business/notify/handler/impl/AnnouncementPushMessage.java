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

package org.ballcat.business.notify.handler.impl;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.ballcat.websocket.message.JsonWebSocketMessage;

/**
 * 公告发布消息
 *
 * @author Hccake 2021/1/5
 * @version 1.0
 */
@Getter
@Setter
public class AnnouncementPushMessage extends JsonWebSocketMessage {

	public AnnouncementPushMessage() {
		super("announcement-push");
	}

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 永久有效的
	 *
	 * @see org.ballcat.common.core.constant.enums.BooleanEnum
	 */
	private Integer immortal;

	/**
	 * 截止日期
	 */
	private LocalDateTime deadline;

}
