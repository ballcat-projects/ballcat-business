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

package org.ballcat.business.system.model.dto;

import java.util.Collection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.business.system.model.entity.SysMenu;
import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.entity.SysUser;

/**
 * 用户信息
 *
 * @author Hccake
 */
@Data
@Schema(title = "用户信息")
public class UserInfoDTO {

	/**
	 * 用户基本信息
	 */
	@Schema(title = "用户基本信息")
	private SysUser sysUser;

	/**
	 * 权限标识集合
	 */
	@Schema(title = "权限标识集合")
	private Collection<String> permissions;

	/**
	 * 角色标识集合
	 */
	@Schema(title = "角色标识集合")
	private Collection<String> roleCodes;

	/**
	 * 菜单对象集合
	 */
	@Schema(title = "菜单对象集合")
	private Collection<SysMenu> menus;

	/**
	 * 角色对象集合
	 */
	@Schema(title = "角色对象集合")
	private Collection<SysRole> roles;

}
