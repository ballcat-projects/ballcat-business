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

import java.util.List;

import org.ballcat.business.system.model.dto.SysMenuCreateDTO;
import org.ballcat.business.system.model.dto.SysMenuUpdateDTO;
import org.ballcat.business.system.model.entity.SysMenu;
import org.ballcat.business.system.model.qo.SysMenuQO;
import org.ballcat.mybatisplus.service.ExtendService;

/**
 * 菜单权限
 *
 * @author hccake 2021-04-06 17:59:51
 */
public interface SysMenuService extends ExtendService<SysMenu> {

	/**
	 * 更新菜单权限
	 * @param sysMenuUpdateDTO 菜单权限修改DTO
	 */
	void update(SysMenuUpdateDTO sysMenuUpdateDTO);

	/**
	 * 查询权限集合，并按sort排序（升序）
	 * @param sysMenuQO 查询条件
	 * @return List<SysMenu>
	 */
	List<SysMenu> listOrderBySort(SysMenuQO sysMenuQO);

	/**
	 * 根据角色标识查询对应的菜单
	 * @param roleCode 角色标识
	 * @return List<SysMenu>
	 */
	List<SysMenu> listByRoleCode(String roleCode);

	/**
	 * 新建菜单权限
	 * @param sysMenuCreateDTO 菜单全新新建传输对象
	 * @return 新建成功返回 true
	 */
	boolean create(SysMenuCreateDTO sysMenuCreateDTO);

}
