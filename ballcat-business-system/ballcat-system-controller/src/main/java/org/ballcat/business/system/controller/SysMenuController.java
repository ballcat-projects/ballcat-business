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

package org.ballcat.business.system.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.system.converter.SysMenuConverter;
import org.ballcat.business.system.enums.SysMenuType;
import org.ballcat.business.system.model.dto.SysMenuCreateDTO;
import org.ballcat.business.system.model.dto.SysMenuUpdateDTO;
import org.ballcat.business.system.model.entity.SysMenu;
import org.ballcat.business.system.model.qo.SysMenuQO;
import org.ballcat.business.system.model.vo.SysMenuGrantVO;
import org.ballcat.business.system.model.vo.SysMenuPageVO;
import org.ballcat.business.system.model.vo.SysMenuRouterVO;
import org.ballcat.business.system.service.SysMenuService;
import org.ballcat.business.system.service.SysUserRoleService;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.common.model.result.R;
import org.ballcat.common.util.Assert;
import org.ballcat.log.operation.annotation.CreateOperationLogging;
import org.ballcat.log.operation.annotation.DeleteOperationLogging;
import org.ballcat.log.operation.annotation.UpdateOperationLogging;
import org.ballcat.security.annotation.Authorize;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单权限
 *
 * @author hccake 2021-04-06 17:59:51
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/menu")
@Tag(name = "菜单权限管理")
public class SysMenuController {

	private final SysMenuService sysMenuService;

	private final SysUserRoleService userRoleService;

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	/**
	 * 返回当前用户的路由集合
	 * @return 当前用户的路由
	 */
	@GetMapping("/router")
	@Operation(summary = "动态路由", description = "动态路由")
	public R<List<SysMenuRouterVO>> getUserPermission() {
		// 获取角色Code
		Long userId = this.principalAttributeAccessor.getUserId();
		Assert.notNull(userId, () -> new SecurityException("获取登录用户信息失败！"));

		// 获取用户角色
		List<String> roleCodes = this.userRoleService.listRoleCodes(userId);
		if (CollectionUtils.isEmpty(roleCodes)) {
			return R.ok(new ArrayList<>());
		}

		// 获取符合条件的权限
		Set<SysMenu> all = new HashSet<>();
		roleCodes.forEach(roleCode -> all.addAll(this.sysMenuService.listByRoleCode(roleCode)));

		// 筛选出菜单
		List<SysMenuRouterVO> menuVOList = all.stream()
			.filter(menuVo -> SysMenuType.BUTTON.getValue() != menuVo.getType())
			.sorted(Comparator.comparingInt(SysMenu::getSort))
			.map(SysMenuConverter.INSTANCE::poToRouterVo)
			.collect(Collectors.toList());

		return R.ok(menuVOList);
	}

	/**
	 * 查询菜单列表
	 * @param sysMenuQO 菜单权限查询对象
	 * @return R 通用返回体
	 */
	@GetMapping("/list")
	@Authorize("hasPermission('system:menu:read')")
	@Operation(summary = "查询菜单列表", description = "查询菜单列表")
	public R<List<SysMenuPageVO>> getSysMenuPage(SysMenuQO sysMenuQO) {
		List<SysMenu> sysMenus = this.sysMenuService.listOrderBySort(sysMenuQO);
		if (CollectionUtils.isEmpty(sysMenus)) {
			R.ok(new ArrayList<>());
		}
		List<SysMenuPageVO> voList = sysMenus.stream()
			.map(SysMenuConverter.INSTANCE::poToPageVo)
			.collect(Collectors.toList());
		return R.ok(voList);
	}

	/**
	 * 查询授权菜单列表
	 * @return R 通用返回体
	 */
	@GetMapping("/grant-list")
	@Authorize("hasPermission('system:menu:read')")
	@Operation(summary = "查询授权菜单列表", description = "查询授权菜单列表")
	public R<List<SysMenuGrantVO>> getSysMenuGrantList() {
		List<SysMenu> sysMenus = this.sysMenuService.list();
		if (CollectionUtils.isEmpty(sysMenus)) {
			R.ok(new ArrayList<>());
		}
		List<SysMenuGrantVO> voList = sysMenus.stream()
			.map(SysMenuConverter.INSTANCE::poToGrantVo)
			.collect(Collectors.toList());
		return R.ok(voList);
	}

	/**
	 * 新增菜单权限
	 * @param sysMenuCreateDTO 菜单权限
	 * @return R 通用返回体
	 */
	@CreateOperationLogging(msg = "新增菜单权限")
	@PostMapping
	@Authorize("hasPermission('system:menu:add')")
	@Operation(summary = "新增菜单权限", description = "新增菜单权限")
	public R<Void> save(@Valid @RequestBody SysMenuCreateDTO sysMenuCreateDTO) {
		return this.sysMenuService.create(sysMenuCreateDTO) ? R.ok()
				: R.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "新增菜单权限失败");
	}

	/**
	 * 修改菜单权限
	 * @param sysMenuUpdateDTO 菜单权限修改DTO
	 * @return R 通用返回体
	 */
	@UpdateOperationLogging(msg = "修改菜单权限")
	@PutMapping
	@Authorize("hasPermission('system:menu:edit')")
	@Operation(summary = "修改菜单权限", description = "修改菜单权限")
	public R<Void> updateById(@RequestBody SysMenuUpdateDTO sysMenuUpdateDTO) {
		this.sysMenuService.update(sysMenuUpdateDTO);
		return R.ok();
	}

	/**
	 * 通过id删除菜单权限
	 * @param id id
	 * @return R 通用返回体
	 */
	@DeleteOperationLogging(msg = "通过id删除菜单权限")
	@DeleteMapping("/{id}")
	@Authorize("hasPermission('system:menu:del')")
	@Operation(summary = "通过id删除菜单权限", description = "通过id删除菜单权限")
	public R<Void> removeById(@PathVariable("id") Long id) {
		return this.sysMenuService.removeById(id) ? R.ok()
				: R.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "通过id删除菜单权限失败");
	}

}
