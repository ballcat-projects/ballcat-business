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

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.ballcat.admin.websocket.constant.AdminWebSocketConstants;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * WebSocket 握手拦截器 在握手时记录下当前 session 对应的用户Id和token信息
 *
 * @author Hccake 2021/1/4
 * @version 1.0
 */
@RequiredArgsConstructor
public class UserAttributeHandshakeInterceptor implements HandshakeInterceptor {

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	/**
	 * Invoked before the handshake is processed.
	 * @param request the current request
	 * @param response the current response
	 * @param wsHandler the target WebSocket handler
	 * @param attributes the attributes from the HTTP handshake to associate with the
	 * WebSocket session; the provided attributes are copied, the original map is not
	 * used.
	 * @return whether to proceed with the handshake ({@code true}) or abort
	 * ({@code false})
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		String accessToken = null;
		// 获得 accessToken
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			accessToken = serverRequest.getServletRequest().getParameter(AdminWebSocketConstants.TOKEN_ATTR_NAME);
		}
		// 由于 WebSocket 握手是由 http 升级的，携带 token 已经被 Security 拦截验证了，所以可以直接获取到用户
		Long userId = this.principalAttributeAccessor.getUserId();
		attributes.put(AdminWebSocketConstants.TOKEN_ATTR_NAME, accessToken);
		attributes.put(AdminWebSocketConstants.USER_KEY_ATTR_NAME, userId);
		return true;
	}

	/**
	 * Invoked after the handshake is done. The response status and headers indicate the
	 * results of the handshake, i.e. whether it was successful or not.
	 * @param request the current request
	 * @param response the current response
	 * @param wsHandler the target WebSocket handler
	 * @param exception an exception raised during the handshake, or {@code null} if none
	 */
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// doNothing
	}

}
