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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.system.model.dto.UserInfoDTO;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.business.system.service.SysUserService;
import org.ballcat.springsecurity.oauth2.constant.UserAttributeNameConstants;
import org.ballcat.springsecurity.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

/**
 * @author Hccake 2019/9/25 20:44
 */
@Slf4j
@RequiredArgsConstructor
public class SysUserDetailsServiceImpl implements UserDetailsService {

	private final SysUserService sysUserService;

	private final UserInfoCoordinator userInfoCoordinator;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = this.sysUserService.getByUsername(username);
		if (sysUser == null) {
			log.error("登录：用户名错误，用户名：{}", username);
			throw new UsernameNotFoundException("username error!");
		}
		UserInfoDTO userInfoDTO = this.sysUserService.findUserInfo(sysUser);
		return getUserDetailsByUserInfo(userInfoDTO);
	}

	/**
	 * 根据UserInfo 获取 UserDetails
	 * @param userInfoDTO 用户信息DTO
	 * @return UserDetails
	 */
	public UserDetails getUserDetailsByUserInfo(UserInfoDTO userInfoDTO) {

		SysUser sysUser = userInfoDTO.getSysUser();
		Collection<String> roleCodes = userInfoDTO.getRoleCodes();
		Collection<String> permissions = userInfoDTO.getPermissions();

		Collection<String> dbAuthsSet = new HashSet<>();
		if (!CollectionUtils.isEmpty(roleCodes)) {
			// 获取角色
			dbAuthsSet.addAll(roleCodes);
			// 获取资源
			dbAuthsSet.addAll(permissions);

		}
		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
			.createAuthorityList(dbAuthsSet.toArray(new String[0]));

		// 默认将角色和权限放入属性中
		HashMap<String, Object> attributes = new HashMap<>(8);
		attributes.put(UserAttributeNameConstants.ROLE_CODES, roleCodes);
		attributes.put(UserAttributeNameConstants.PERMISSIONS, permissions);

		// 用户额外属性
		this.userInfoCoordinator.coordinateAttribute(userInfoDTO, attributes);

		return User.builder()
			.userId(sysUser.getUserId())
			.username(sysUser.getUsername())
			.password(sysUser.getPassword())
			.nickname(sysUser.getNickname())
			.avatar(sysUser.getAvatar())
			.status(sysUser.getStatus())
			.organizationId(sysUser.getOrganizationId())
			.email(sysUser.getEmail())
			.phoneNumber(sysUser.getPhoneNumber())
			.gender(sysUser.getGender())
			.type(sysUser.getType())
			.authorities(authorities)
			.attributes(attributes)
			.build();
	}

}
