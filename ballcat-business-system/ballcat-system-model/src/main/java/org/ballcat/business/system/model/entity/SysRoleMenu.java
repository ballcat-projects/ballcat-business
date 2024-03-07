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

package org.ballcat.business.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色菜单表
 *
 * @author ballcat code generator 2019-10-14 17:42:23
 */
@Data
@TableName("sys_role_menu")
@Schema(title = "角色菜单")
public class SysRoleMenu {

	private static final long serialVersionUID = 1L;

	public SysRoleMenu() {
	}

	public SysRoleMenu(String roleCode, Long menuId) {
		this.roleCode = roleCode;
		this.menuId = menuId;
	}

	@TableId
	private Long id;

	/**
	 * 角色 Code
	 */
	@Schema(title = "角色 Code")
	private String roleCode;

	/**
	 * 权限ID
	 */
	@Schema(title = "菜单id")
	private Long menuId;

}
