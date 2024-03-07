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

package org.ballcat.business.system.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.ballcat.business.system.mapper.SysRoleMenuMapper;
import org.ballcat.business.system.model.entity.SysRoleMenu;
import org.ballcat.business.system.service.SysRoleMenuService;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author hccake
 */
@Service
public class SysRoleMenuServiceImpl extends ExtendServiceImpl<SysRoleMenuMapper, SysRoleMenu>
		implements SysRoleMenuService {

	/**
	 * @param roleCode 角色
	 * @param menuIds 权限ID集合
	 * @return boolean
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveRoleMenus(String roleCode, Long[] menuIds) {
		// 1、先删除旧数据
		baseMapper.deleteByRoleCode(roleCode);
		if (menuIds == null || menuIds.length == 0) {
			return Boolean.TRUE;
		}

		// 2、再批量插入新数据
		List<SysRoleMenu> list = Arrays.stream(menuIds)
			.map(menuId -> new SysRoleMenu(roleCode, menuId))
			.collect(Collectors.toList());
		int i = baseMapper.insertBatchSomeColumn(list);
		return SqlHelper.retBool(i);
	}

	/**
	 * 根据权限ID删除角色权限关联数据
	 * @param menuId 权限ID
	 */
	@Override
	public void deleteByMenuId(Serializable menuId) {
		baseMapper.deleteByMenuId(menuId);
	}

	/**
	 * 根据角色标识删除角色权限关联关系
	 * @param roleCode 角色标识
	 */
	@Override
	public void deleteByRoleCode(String roleCode) {
		baseMapper.deleteByRoleCode(roleCode);
	}

	/**
	 * 更新某个菜单的 id
	 * @param originalId 原菜单ID
	 * @param menuId 修改后的菜单Id
	 * @return 被更新的菜单数
	 */
	@Override
	public int updateMenuId(Long originalId, Long menuId) {
		return baseMapper.updateMenuId(originalId, menuId);
	}

}
