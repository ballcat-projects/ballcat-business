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
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.ballcat.business.system.model.entity.SysMenu;
import org.ballcat.business.system.model.qo.SysMenuQO;
import org.ballcat.mybatisplus.conditions.query.LambdaQueryWrapperX;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.ballcat.mybatisplus.toolkit.WrappersX;

/**
 * 菜单权限
 *
 * @author hccake 2021-04-01 22:08:13
 */
public interface SysMenuMapper extends ExtendMapper<SysMenu> {

	/**
	 * 查询权限集合，并按sort排序（升序）
	 * @param sysMenuQO 查询条件
	 * @return List<SysMenu>
	 */
	default List<SysMenu> listOrderBySort(SysMenuQO sysMenuQO) {
		LambdaQueryWrapperX<SysMenu> wrapper = WrappersX.lambdaQueryX(SysMenu.class)
			.likeIfPresent(SysMenu::getId, sysMenuQO.getId())
			.likeIfPresent(SysMenu::getTitle, sysMenuQO.getTitle())
			.likeIfPresent(SysMenu::getPermission, sysMenuQO.getPermission())
			.likeIfPresent(SysMenu::getPath, sysMenuQO.getPath());
		wrapper.orderByAsc(SysMenu::getSort);
		return this.selectList(wrapper);
	}

	/**
	 * 根据角色标识查询对应的菜单
	 * @param roleCode 角色标识
	 * @return List<SysMenu>
	 */
	List<SysMenu> listByRoleCode(String roleCode);

	/**
	 * 查询指定权限的下级权限总数
	 * @param id 权限ID
	 * @return 下级权限总数
	 */
	default Long countSubMenu(Serializable id) {
		return this.selectCount(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getParentId, id));
	}

	/**
	 * 根据指定的 id 更新 菜单权限（便于修改其 id）
	 * @param sysMenu 系统菜单
	 * @param originalId 原菜单ID
	 * @return 更新成功返回 true
	 */
	default boolean updateMenuAndId(Long originalId, SysMenu sysMenu) {
		// @formatter:off
		LambdaUpdateWrapper<SysMenu> wrapper = Wrappers.lambdaUpdate(SysMenu.class)
				.set(SysMenu::getId, sysMenu.getId())
				.eq(SysMenu::getId, originalId);
		// @formatter:on
		int flag = this.update(sysMenu, wrapper);
		return SqlHelper.retBool(flag);
	}

	/**
	 * 根据指定的 parentId 找到对应的菜单，更新其 parentId
	 * @param originalParentId 原 parentId
	 * @param parentId 现 parentId
	 * @return 更新条数不为 0 时，返回 true
	 */
	default boolean updateParentId(Long originalParentId, Long parentId) {
		// @formatter:off
		LambdaUpdateWrapper<SysMenu> wrapper = Wrappers.lambdaUpdate(SysMenu.class)
				.set(SysMenu::getParentId, parentId)
				.eq(SysMenu::getParentId, originalParentId);
		// @formatter:on
		int flag = this.update(null, wrapper);
		return SqlHelper.retBool(flag);
	}

}
