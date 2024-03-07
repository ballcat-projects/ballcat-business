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

import org.ballcat.business.system.model.entity.SysUser;

/**
 * 超级管理员账户规则配置
 *
 * @author lingting 2020-06-24 21:00:15
 */
public interface AdminUserChecker {

	/**
	 * 校验用户是否为超级管理员
	 * @param user 用户信息
	 * @return boolean
	 */
	boolean isAdminUser(SysUser user);

	/**
	 * 修改权限校验
	 * @param targetUser 目标用户
	 * @return 是否有权限修改目标用户
	 */
	boolean hasModifyPermission(SysUser targetUser);

}
