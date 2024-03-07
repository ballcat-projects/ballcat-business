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

package org.ballcat.admin.websocket.component;

import lombok.RequiredArgsConstructor;
import org.ballcat.admin.websocket.constant.AdminWebSocketConstants;
import org.ballcat.websocket.session.SessionKeyGenerator;
import org.springframework.web.socket.WebSocketSession;

/**
 * <p>
 * 用户 WebSocketSession 唯一标识生成器
 * </p>
 * <p>
 * 此类主要使用当前 session 对应用户的唯一标识做为 session 的唯一标识 方便系统快速通过用户获取对应 session
 *
 * @author Hccake 2021/1/5
 * @version 1.0
 */
@RequiredArgsConstructor
public class UserSessionKeyGenerator implements SessionKeyGenerator {

	/**
	 * 获取当前session的唯一标识，用户的唯一标识已经通过
	 * @param webSocketSession 当前session
	 * @return session唯一标识
	 * @see UserAttributeHandshakeInterceptor 存储在当前 session 的属性中
	 */
	@Override
	public Object sessionKey(WebSocketSession webSocketSession) {
		return webSocketSession.getAttributes().get(AdminWebSocketConstants.USER_KEY_ATTR_NAME);
	}

}
