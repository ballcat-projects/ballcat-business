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

package org.ballcat.admin.springsecurity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ballcat.business.system.model.vo.SysUserInfo;
import org.ballcat.common.util.JsonUtils;
import org.ballcat.springsecurity.oauth2.constant.TokenAttributeNameConstants;
import org.ballcat.springsecurity.oauth2.constant.UserAttributeNameConstants;
import org.ballcat.springsecurity.userdetails.User;
import org.ballcat.springsecurity.web.FormLoginSuccessHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 使用 Spring Security 前后端分离时的表单登录成功处理器
 *
 * @author Hccake
 * @since 2.0.0
 */
public class SpringSecuritySeparationFormLoginSuccessHandler implements FormLoginSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		Object principal = Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
			.map(Authentication::getPrincipal)
			.orElse(null);

		Map<String, Object> additionalParameters = new HashMap<>();
		if (principal instanceof User) {
			User user = (User) principal;
			// 用户基本信息
			SysUserInfo sysUserInfo = getSysUserInfo(user);
			additionalParameters.put(TokenAttributeNameConstants.INFO, sysUserInfo);

			// 默认在登录时只把角色和权限的信息返回
			Map<String, Object> resultAttributes = new HashMap<>(2);
			Map<String, Object> attributes = user.getAttributes();
			resultAttributes.put(UserAttributeNameConstants.ROLE_CODES,
					attributes.get(UserAttributeNameConstants.ROLE_CODES));
			resultAttributes.put(UserAttributeNameConstants.PERMISSIONS,
					attributes.get(UserAttributeNameConstants.PERMISSIONS));
			additionalParameters.put(TokenAttributeNameConstants.ATTRIBUTES, resultAttributes);
		}

		response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
		response.setStatus(HttpStatus.OK.value());

		response.getWriter().write(JsonUtils.toJson(additionalParameters));
	}

	/**
	 * 根据 User 对象获取 SysUserInfo
	 * @param user User
	 * @return SysUserInfo
	 */
	public SysUserInfo getSysUserInfo(User user) {
		SysUserInfo sysUserInfo = new SysUserInfo();
		sysUserInfo.setUserId(user.getUserId());
		sysUserInfo.setUsername(user.getUsername());
		sysUserInfo.setNickname(user.getNickname());
		sysUserInfo.setAvatar(user.getAvatar());
		sysUserInfo.setOrganizationId(user.getOrganizationId());
		sysUserInfo.setType(user.getType());
		sysUserInfo.setPhoneNumber(user.getPhoneNumber());
		sysUserInfo.setEmail(user.getEmail());
		sysUserInfo.setGender(user.getGender());
		return sysUserInfo;
	}

}
