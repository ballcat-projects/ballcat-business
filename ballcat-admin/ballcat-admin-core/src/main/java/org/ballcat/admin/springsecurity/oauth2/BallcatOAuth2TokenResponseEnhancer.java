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

package org.ballcat.admin.springsecurity.oauth2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.ballcat.business.system.model.vo.SysUserInfo;
import org.ballcat.springsecurity.oauth2.constant.TokenAttributeNameConstants;
import org.ballcat.springsecurity.oauth2.constant.UserAttributeNameConstants;
import org.ballcat.springsecurity.oauth2.server.authorization.web.authentication.OAuth2TokenResponseEnhancer;
import org.ballcat.springsecurity.oauth2.userdetails.DefaultOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;

/**
 * token 响应增强
 *
 * @author Hccake
 */
public class BallcatOAuth2TokenResponseEnhancer implements OAuth2TokenResponseEnhancer {

	@Override
	public Map<String, Object> enhance(OAuth2AccessTokenAuthenticationToken accessTokenAuthentication) {
		Object principal = Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
			.map(Authentication::getPrincipal)
			.orElse(null);

		// token 附属信息
		Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
		if (additionalParameters == null) {
			additionalParameters = new HashMap<>(8);
		}

		if (principal instanceof DefaultOAuth2User) {
			DefaultOAuth2User user = (DefaultOAuth2User) principal;
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

		return additionalParameters;
	}

	/**
	 * 根据 User 对象获取 SysUserInfo
	 * @param user User
	 * @return SysUserInfo
	 */
	public SysUserInfo getSysUserInfo(DefaultOAuth2User user) {
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
