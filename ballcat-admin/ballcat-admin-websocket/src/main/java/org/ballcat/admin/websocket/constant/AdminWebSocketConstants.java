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

package org.ballcat.admin.websocket.constant;

/**
 * @author Hccake 2021/1/5
 * @version 1.0
 */
public final class AdminWebSocketConstants {

	private AdminWebSocketConstants() {
	}

	/**
	 * 存储在 WebSocketSession Attribute 中的 token 属性名
	 */
	public static final String TOKEN_ATTR_NAME = "access_token";

	/**
	 * 存储在 WebSocketSession Attribute 中的 用户唯一标识 属性名
	 */
	public static final String USER_KEY_ATTR_NAME = "userId";

}
