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

package org.ballcat.business.system.checker;

import lombok.RequiredArgsConstructor;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.business.system.properties.SystemProperties;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 超级管理员账户规则配置
 *
 * @author lingting 2020-06-24 21:00:15
 */
@Service
@RequiredArgsConstructor
public class AdminUserCheckerImpl implements AdminUserChecker {

	private final SystemProperties systemProperties;

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	@Override
	public boolean isAdminUser(SysUser user) {
		SystemProperties.Administrator administrator = this.systemProperties.getAdministrator();
		if (administrator.getUserId() == user.getUserId()) {
			return true;
		}
		return StringUtils.hasText(administrator.getUsername())
				&& administrator.getUsername().equals(user.getUsername());
	}

	@Override
	public boolean hasModifyPermission(SysUser targetUser) {
		// 如果需要修改的用户是超级管理员，则只能本人修改
		if (this.isAdminUser(targetUser)) {
			return this.principalAttributeAccessor.getUsername().equals(targetUser.getUsername());
		}
		return true;
	}

}
