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
import java.util.List;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.system.converter.SysMenuConverter;
import org.ballcat.business.system.mapper.SysMenuMapper;
import org.ballcat.business.system.model.dto.SysMenuCreateDTO;
import org.ballcat.business.system.model.dto.SysMenuUpdateDTO;
import org.ballcat.business.system.model.entity.SysMenu;
import org.ballcat.business.system.model.qo.SysMenuQO;
import org.ballcat.business.system.service.SysMenuService;
import org.ballcat.business.system.service.SysRoleMenuService;
import org.ballcat.common.core.exception.BusinessException;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.common.util.Assert;
import org.ballcat.i18n.I18nMessage;
import org.ballcat.i18n.I18nMessageCreateEvent;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 菜单权限
 *
 * @author hccake 2021-04-06 17:59:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ExtendServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

	private final SysRoleMenuService sysRoleMenuService;

	private final ApplicationEventPublisher eventPublisher;

	/**
	 * 插入一条记录（选择字段，策略插入）
	 * @param sysMenu 实体对象
	 */
	@Override
	public boolean save(SysMenu sysMenu) {
		Long menuId = sysMenu.getId();
		SysMenu existingMenu = baseMapper.selectById(menuId);
		if (existingMenu != null) {
			String errorMessage = String.format("ID [%s] 已被菜单 [%s] 使用，请更换其他菜单ID", menuId, existingMenu.getTitle());
			throw new BusinessException(BaseResultCode.LOGIC_CHECK_ERROR.getCode(), errorMessage);
		}
		return SqlHelper.retBool(baseMapper.insert(sysMenu));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean create(SysMenuCreateDTO sysMenuCreateDTO) {

		SysMenu sysMenu = SysMenuConverter.INSTANCE.createDtoToPo(sysMenuCreateDTO);
		Long menuId = sysMenu.getId();
		if (menuId != null) {
			SysMenu existingMenu = baseMapper.selectById(menuId);
			if (existingMenu != null) {
				String errorMessage = String.format("ID [%s] 已被菜单 [%s] 使用，请更换其他菜单ID", menuId, existingMenu.getTitle());
				throw new BusinessException(BaseResultCode.LOGIC_CHECK_ERROR.getCode(), errorMessage);
			}
		}

		boolean saveSuccess = SqlHelper.retBool(baseMapper.insert(sysMenu));
		Assert.isTrue(saveSuccess, () -> {
			log.error("[create] 创建菜单失败，sysMenuCreateDTO: {}", sysMenuCreateDTO);
			return new BusinessException(BaseResultCode.UPDATE_DATABASE_ERROR.getCode(), "创建菜单失败");
		});

		// 多语言保存事件发布
		List<I18nMessage> i18nMessages = sysMenuCreateDTO.getI18nMessages();
		if (!CollectionUtils.isEmpty(i18nMessages)) {
			this.eventPublisher.publishEvent(new I18nMessageCreateEvent(i18nMessages));
		}

		return saveSuccess;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		// 查询当前权限是否有子权限
		Long subMenu = baseMapper.countSubMenu(id);
		if (subMenu != null && subMenu > 0) {
			throw new BusinessException(BaseResultCode.LOGIC_CHECK_ERROR.getCode(), "菜单含有下级不能删除");
		}
		// 删除角色权限关联数据
		this.sysRoleMenuService.deleteByMenuId(id);
		// 删除当前菜单及其子菜单
		return SqlHelper.retBool(baseMapper.deleteById(id));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysMenuUpdateDTO sysMenuUpdateDTO) {
		// 原来的菜单 Id
		Long originalId = sysMenuUpdateDTO.getOriginalId();
		SysMenu sysMenu = SysMenuConverter.INSTANCE.updateDtoToPo(sysMenuUpdateDTO);

		// 更新菜单信息
		boolean updateSuccess = baseMapper.updateMenuAndId(originalId, sysMenu);
		Assert.isTrue(updateSuccess, () -> {
			log.error("[update] 更新菜单权限时，sql 执行异常，originalId：{}，sysMenu：{}", originalId, sysMenu);
			return new BusinessException(BaseResultCode.UPDATE_DATABASE_ERROR.getCode(), "更新菜单权限时，sql 执行异常");
		});

		// 如果未修改过 菜单id 直接返回
		Long menuId = sysMenuUpdateDTO.getId();
		if (originalId.equals(menuId)) {
			return;
		}

		// 修改过菜单id，则需要对应修改角色菜单的关联表信息，这里不需要 check，因为可能没有授权过该菜单，所以返回值为 0
		this.sysRoleMenuService.updateMenuId(originalId, menuId);
		// 更新子菜单的 parentId
		baseMapper.updateParentId(originalId, menuId);
	}

	/**
	 * 查询权限集合，并按sort排序（升序）
	 * @param sysMenuQO 查询条件
	 * @return List<SysMenu>
	 */
	@Override
	public List<SysMenu> listOrderBySort(SysMenuQO sysMenuQO) {
		return baseMapper.listOrderBySort(sysMenuQO);
	}

	/**
	 * 根据角色标识查询对应的菜单
	 * @param roleCode 角色标识
	 * @return List<SysMenu>
	 */
	@Override
	public List<SysMenu> listByRoleCode(String roleCode) {
		return baseMapper.listByRoleCode(roleCode);
	}

}
