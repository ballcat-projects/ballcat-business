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

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.system.constant.SysRoleConst;
import org.ballcat.business.system.converter.SysRoleConverter;
import org.ballcat.business.system.model.dto.SysRoleUpdateDTO;
import org.ballcat.business.system.model.entity.SysMenu;
import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.qo.RoleBindUserQO;
import org.ballcat.business.system.model.qo.SysRoleQO;
import org.ballcat.business.system.model.vo.RoleBindUserVO;
import org.ballcat.business.system.model.vo.SysRolePageVO;
import org.ballcat.business.system.service.SysMenuService;
import org.ballcat.business.system.service.SysRoleMenuService;
import org.ballcat.business.system.service.SysRoleService;
import org.ballcat.business.system.service.SysUserRoleService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.domain.SelectData;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.common.model.result.R;
import org.ballcat.log.operation.annotation.CreateOperationLogging;
import org.ballcat.log.operation.annotation.DeleteOperationLogging;
import org.ballcat.log.operation.annotation.UpdateOperationLogging;
import org.ballcat.security.annotation.Authorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hccake
 */
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
@Tag(name = "角色管理模块")
public class SysRoleController {

	private final SysRoleService sysRoleService;

	private final SysMenuService sysMenuService;

	private final SysUserRoleService sysUserRoleService;

	private final SysRoleMenuService sysRoleMenuService;

	/**
	 * 分页查询角色信息
	 * @param pageParam 分页参数
	 * @return PageResult 分页结果
	 */
	@GetMapping("/page")
	@Authorize("hasPermission('system:role:read')")
	public R<PageResult<SysRolePageVO>> getRolePage(@Validated PageParam pageParam, SysRoleQO sysRoleQo) {
		return R.ok(this.sysRoleService.queryPage(pageParam, sysRoleQo));
	}

	/**
	 * 通过ID查询角色信息
	 * @param id ID
	 * @return 角色信息
	 */
	@GetMapping("/{id}")
	@Authorize("hasPermission('system:role:read')")
	public R<SysRole> getById(@PathVariable("id") Long id) {
		return R.ok(this.sysRoleService.getById(id));
	}

	/**
	 * 新增系统角色表
	 * @param sysRole 系统角色表
	 * @return R
	 */
	@CreateOperationLogging(msg = "新增系统角色")
	@PostMapping
	@Authorize("hasPermission('system:role:add')")
	@Operation(summary = "新增系统角色", description = "新增系统角色")
	public R<Boolean> save(@Valid @RequestBody SysRole sysRole) {
		return this.sysRoleService.save(sysRole) ? R.ok() : R.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "新建角色失败");
	}

	/**
	 * 修改角色
	 * @param roleUpdateDTO 角色修改DTO
	 * @return success/false
	 */
	@UpdateOperationLogging(msg = "修改系统角色")
	@PutMapping
	@Authorize("hasPermission('system:role:edit')")
	@Operation(summary = "修改系统角色", description = "修改系统角色")
	public R<Boolean> update(@Valid @RequestBody SysRoleUpdateDTO roleUpdateDTO) {
		SysRole sysRole = SysRoleConverter.INSTANCE.dtoToPo(roleUpdateDTO);
		return R.ok(this.sysRoleService.updateById(sysRole));
	}

	/**
	 * 删除角色
	 * @param id id
	 * @return 结果信息
	 */
	@DeleteMapping("/{id}")
	@DeleteOperationLogging(msg = "通过id删除系统角色")
	@Authorize("hasPermission('system:role:del')")
	@Operation(summary = "通过id删除系统角色", description = "通过id删除系统角色")
	public R<Boolean> removeById(@PathVariable("id") Long id) {
		SysRole oldRole = this.sysRoleService.getById(id);
		if (oldRole == null) {
			return R.ok();
		}
		if (SysRoleConst.Type.SYSTEM.getValue().equals(oldRole.getType())) {
			return R.failed(BaseResultCode.LOGIC_CHECK_ERROR, "系统角色不允许被删除!");
		}
		return R.ok(this.sysRoleService.removeById(id));
	}

	/**
	 * 获取角色列表
	 * @return 角色列表
	 */
	@GetMapping("/list")
	public R<List<SysRole>> listRoles() {
		return R.ok(this.sysRoleService.list());
	}

	/**
	 * 更新角色权限
	 * @param roleCode 角色Code
	 * @param permissionIds 权限ID数组
	 * @return success、false
	 */
	@PutMapping("/permission/code/{roleCode}")
	@UpdateOperationLogging(msg = "更新角色权限")
	@Authorize("hasPermission('system:role:grant')")
	@Operation(summary = "更新角色权限", description = "更新角色权限")
	public R<Boolean> savePermissionIds(@PathVariable("roleCode") String roleCode, @RequestBody Long[] permissionIds) {
		return R.ok(this.sysRoleMenuService.saveRoleMenus(roleCode, permissionIds));
	}

	/**
	 * 返回角色的菜单集合
	 * @param roleCode 角色ID
	 * @return 属性集合
	 */
	@GetMapping("/permission/code/{roleCode}")
	public R<List<Long>> getPermissionIds(@PathVariable("roleCode") String roleCode) {
		List<SysMenu> sysMenus = this.sysMenuService.listByRoleCode(roleCode);
		List<Long> menuIds = sysMenus.stream().map(SysMenu::getId).collect(Collectors.toList());
		return R.ok(menuIds);
	}

	/**
	 * 获取角色列表
	 * @return 角色列表
	 */
	@GetMapping("/select")
	public R<List<SelectData<Void>>> listSelectData() {
		return R.ok(this.sysRoleService.listSelectData());
	}

	/**
	 * 分页查询已授权指定角色的用户列表
	 * @param roleBindUserQO 角色绑定用户的查询条件
	 * @return R
	 */
	@GetMapping("/user/page")
	@Authorize("hasPermission('system:role:grant')")
	@Operation(summary = "查看已授权指定角色的用户列表", description = "查看已授权指定角色的用户列表")
	public R<PageResult<RoleBindUserVO>> queryUserPageByRoleCode(PageParam pageParam,
			@Valid RoleBindUserQO roleBindUserQO) {
		return R.ok(this.sysUserRoleService.queryUserPageByRoleCode(pageParam, roleBindUserQO));
	}

	/**
	 * 解绑与用户绑定关系
	 * @return R
	 */
	@DeleteMapping("/user")
	@Authorize("hasPermission('system:role:grant')")
	@Operation(summary = "解绑与用户绑定关系", description = "解绑与用户绑定关系")
	public R<Boolean> unbindRoleUser(@RequestParam("userId") Long userId, @RequestParam("roleCode") String roleCode) {
		return R.ok(this.sysUserRoleService.unbindRoleUser(userId, roleCode));
	}

}
