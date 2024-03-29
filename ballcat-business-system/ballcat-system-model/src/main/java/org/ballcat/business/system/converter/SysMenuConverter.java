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

package org.ballcat.business.system.converter;

import org.ballcat.business.system.model.dto.SysMenuCreateDTO;
import org.ballcat.business.system.model.dto.SysMenuUpdateDTO;
import org.ballcat.business.system.model.entity.SysMenu;
import org.ballcat.business.system.model.vo.SysMenuGrantVO;
import org.ballcat.business.system.model.vo.SysMenuPageVO;
import org.ballcat.business.system.model.vo.SysMenuRouterVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 菜单权限模型转换器
 *
 * @author hccake 2021-04-06 17:59:51
 */
@Mapper
public interface SysMenuConverter {

	SysMenuConverter INSTANCE = Mappers.getMapper(SysMenuConverter.class);

	/**
	 * PO 转 PageVO
	 * @param sysMenu 菜单权限实体
	 * @return SysMenuPageVO 菜单权限PageVO
	 */
	@Mapping(target = "i18nTitle", source = "title")
	SysMenuPageVO poToPageVo(SysMenu sysMenu);

	/**
	 * PO 转 GrantVo
	 * @param sysMenu 菜单权限实体
	 * @return SysMenuPageVO 菜单权限GrantVO
	 */
	SysMenuGrantVO poToGrantVo(SysMenu sysMenu);

	/**
	 * PO 转 VO
	 * @param sysMenu 菜单权限实体
	 * @return SysMenuVO
	 */
	SysMenuRouterVO poToRouterVo(SysMenu sysMenu);

	/**
	 * createDto 转 Po
	 * @param sysMenuCreateDTO 菜单新建对象
	 * @return SysMenu 菜单权限的持久化对象
	 */
	SysMenu createDtoToPo(SysMenuCreateDTO sysMenuCreateDTO);

	/**
	 * updateDto 转 Po
	 * @param sysMenuUpdateDTO 菜单修改对象
	 * @return SysMenu 菜单权限的持久化对象
	 */
	SysMenu updateDtoToPo(SysMenuUpdateDTO sysMenuUpdateDTO);

}
