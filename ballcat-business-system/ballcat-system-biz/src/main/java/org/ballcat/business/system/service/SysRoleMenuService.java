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

package org.ballcat.business.system.service;

import java.io.Serializable;

import org.ballcat.business.system.model.entity.SysRoleMenu;
import org.ballcat.mybatisplus.service.ExtendService;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author hccake
 * @since 2017-10-29
 */
public interface SysRoleMenuService extends ExtendService<SysRoleMenu> {

	/**
	 * 更新角色菜单
	 * @param roleCode 角色
	 * @param menuIds 权限ID数组
	 * @return 更新角色权限关联关系是否成功
	 */
	Boolean saveRoleMenus(String roleCode, Long[] menuIds);

	/**
	 * 根据权限ID删除角色权限关联数据
	 * @param menuId 权限ID
	 */
	void deleteByMenuId(Serializable menuId);

	/**
	 * 根据角色标识删除角色权限关联关系
	 * @param roleCode 角色标识
	 */
	void deleteByRoleCode(String roleCode);

	/**
	 * 更新某个菜单的 id
	 * @param originalId 原菜单ID
	 * @param menuId 修改后的菜单Id
	 * @return 被更新的菜单数
	 */
	int updateMenuId(Long originalId, Long menuId);

}
