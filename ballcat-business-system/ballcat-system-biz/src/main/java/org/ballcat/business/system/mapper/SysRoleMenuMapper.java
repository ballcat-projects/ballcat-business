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

package org.ballcat.business.system.mapper;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.ballcat.business.system.model.entity.SysRoleMenu;
import org.ballcat.mybatisplus.mapper.ExtendMapper;

/**
 * <p>
 * 角色菜单表 Mapper 接口
 * </p>
 *
 * @author hccake
 * @since 2017-10-29
 */
public interface SysRoleMenuMapper extends ExtendMapper<SysRoleMenu> {

	/**
	 * 根据权限ID删除角色权限关联关系
	 * @param menuId 权限ID
	 */
	default void deleteByMenuId(Serializable menuId) {
		this.delete(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getMenuId, menuId));
	}

	/**
	 * 根据角色标识删除角色权限关联关系
	 * @param roleCode 角色标识
	 */
	default void deleteByRoleCode(String roleCode) {
		this.delete(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleCode, roleCode));
	}

	/**
	 * 更新某个菜单的 id
	 * @param originalId 原菜单ID
	 * @param menuId 修改后的菜单Id
	 * @return 被更新的条数
	 */
	default int updateMenuId(Long originalId, Long menuId) {
		// @formatter:off
		LambdaUpdateWrapper<SysRoleMenu> wrapper = Wrappers.lambdaUpdate(SysRoleMenu.class)
				.set(SysRoleMenu::getMenuId, menuId)
				.eq(SysRoleMenu::getMenuId, originalId);
		// @formatter:on

		return this.update(null, wrapper);
	}

}
