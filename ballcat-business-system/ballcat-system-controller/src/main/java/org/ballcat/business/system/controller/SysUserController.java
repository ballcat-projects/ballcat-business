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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.system.component.PasswordHelper;
import org.ballcat.business.system.constant.SysUserConst;
import org.ballcat.business.system.converter.SysUserConverter;
import org.ballcat.business.system.model.dto.SysUserDTO;
import org.ballcat.business.system.model.dto.SysUserPassDTO;
import org.ballcat.business.system.model.dto.SysUserScope;
import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.business.system.model.qo.SysUserQO;
import org.ballcat.business.system.model.vo.SysUserInfo;
import org.ballcat.business.system.model.vo.SysUserPageVO;
import org.ballcat.business.system.service.SysUserRoleService;
import org.ballcat.business.system.service.SysUserService;
import org.ballcat.common.core.validation.group.CreateGroup;
import org.ballcat.common.core.validation.group.UpdateGroup;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.domain.SelectData;
import org.ballcat.common.model.result.ApiResult;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.common.model.result.SystemResultCode;
import org.ballcat.log.operation.annotation.CreateOperationLogging;
import org.ballcat.log.operation.annotation.DeleteOperationLogging;
import org.ballcat.log.operation.annotation.UpdateOperationLogging;
import org.ballcat.security.annotation.Authorize;
import org.springframework.util.CollectionUtils;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 组织架构
 *
 * @author hccake 2020-09-24 20:16:15
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
@Tag(name = "用户管理模块")
public class SysUserController {

	private final SysUserService sysUserService;

	private final SysUserRoleService sysUserRoleService;

	private final PasswordHelper passwordHelper;

	/**
	 * 分页查询用户
	 * @param pageParam 参数集
	 * @return 用户集合
	 */
	@GetMapping("/page")
	@Authorize("hasPermission('system:user:read')")
	@Operation(summary = "分页查询系统用户")
	public ApiResult<PageResult<SysUserPageVO>> getUserPage(@Validated PageParam pageParam, SysUserQO qo) {
		return ApiResult.ok(this.sysUserService.queryPage(pageParam, qo));
	}

	/**
	 * 获取用户Select
	 * @return 用户SelectData
	 */
	@GetMapping("/select")
	@Authorize("hasPermission('system:user:read')")
	@Operation(summary = "获取用户下拉列表数据")
	public ApiResult<List<SelectData<Void>>> listSelectData(
			@RequestParam(value = "userTypes", required = false) List<Integer> userTypes) {
		return ApiResult.ok(this.sysUserService.listSelectData(userTypes));
	}

	/**
	 * 获取指定用户的基本信息
	 * @param userId 用户ID
	 * @return SysUserInfo
	 */
	@GetMapping("/{userId}")
	@Authorize("hasPermission('system:user:read')")
	@Operation(summary = "获取指定用户的基本信息")
	public ApiResult<SysUserInfo> getSysUserInfo(@PathVariable("userId") Long userId) {
		SysUser sysUser = this.sysUserService.getById(userId);
		if (sysUser == null) {
			return ApiResult.ok();
		}
		SysUserInfo sysUserInfo = SysUserConverter.INSTANCE.poToInfo(sysUser);
		return ApiResult.ok(sysUserInfo);
	}

	/**
	 * 新增用户
	 * @param sysUserDTO userInfo
	 * @return success/false
	 */
	@PostMapping
	@CreateOperationLogging(msg = "新增系统用户")
	@Authorize("hasPermission('system:user:add')")
	@Operation(summary = "新增系统用户", description = "新增系统用户")
	public ApiResult<Void> addSysUser(
			@Validated({ Default.class, CreateGroup.class }) @RequestBody SysUserDTO sysUserDTO) {
		SysUser user = this.sysUserService.getByUsername(sysUserDTO.getUsername());
		if (user != null) {
			return ApiResult.failed(BaseResultCode.LOGIC_CHECK_ERROR, "用户名已存在");
		}

		// 明文密码
		String rawPassword = this.passwordHelper.decodeAes(sysUserDTO.getPass());
		sysUserDTO.setPassword(rawPassword);

		// 密码规则校验
		if (this.passwordHelper.validateRule(rawPassword)) {
			return this.sysUserService.addSysUser(sysUserDTO) ? ApiResult.ok()
					: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "新增系统用户失败");
		}
		else {
			return ApiResult.failed(SystemResultCode.BAD_REQUEST, "密码格式不符合规则!");
		}
	}

	/**
	 * 修改用户个人信息
	 * @param sysUserDto userInfo
	 * @return success/false
	 */
	@PutMapping
	@UpdateOperationLogging(msg = "修改系统用户")
	@Authorize("hasPermission('system:user:edit')")
	@Operation(summary = "修改系统用户", description = "修改系统用户")
	public ApiResult<Void> updateUserInfo(
			@Validated({ Default.class, UpdateGroup.class }) @RequestBody SysUserDTO sysUserDto) {
		return this.sysUserService.updateSysUser(sysUserDto) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "修改系统用户失败");
	}

	/**
	 * 删除用户信息
	 */
	@DeleteMapping("/{userId}")
	@DeleteOperationLogging(msg = "通过id删除系统用户")
	@Authorize("hasPermission('system:user:del')")
	@Operation(summary = "通过id删除系统用户", description = "通过id删除系统用户")
	public ApiResult<Void> deleteByUserId(@PathVariable("userId") Long userId) {
		return this.sysUserService.deleteByUserId(userId) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "删除系统用户失败");
	}

	/**
	 * 获取用户 所拥有的角色ID
	 * @param userId userId
	 */
	@GetMapping("/scope/{userId}")
	@Authorize("hasPermission('system:user:grant')")
	public ApiResult<SysUserScope> getUserRoleIds(@PathVariable("userId") Long userId) {

		List<SysRole> roleList = this.sysUserRoleService.listRoles(userId);

		List<String> roleCodes = new ArrayList<>();
		if (!CollectionUtils.isEmpty(roleList)) {
			roleList.forEach(role -> roleCodes.add(role.getCode()));
		}

		SysUserScope sysUserScope = new SysUserScope();
		sysUserScope.setRoleCodes(roleCodes);

		return ApiResult.ok(sysUserScope);
	}

	/**
	 * 修改用户权限信息 比如角色 数据权限等
	 * @param sysUserScope sysUserScope
	 * @return success/false
	 */
	@PutMapping("/scope/{userId}")
	@UpdateOperationLogging(msg = "系统用户授权")
	@Authorize("hasPermission('system:user:grant')")
	@Operation(summary = "系统用户授权", description = "系统用户授权")
	public ApiResult<Void> updateUserScope(@PathVariable("userId") Long userId,
			@RequestBody SysUserScope sysUserScope) {
		return this.sysUserService.updateUserScope(userId, sysUserScope) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "系统用户授权失败");
	}

	/**
	 * 修改用户密码
	 */
	@PutMapping("/pass/{userId}")
	@UpdateOperationLogging(msg = "修改系统用户密码")
	@Authorize("hasPermission('system:user:pass')")
	@Operation(summary = "修改系统用户密码", description = "修改系统用户密码")
	public ApiResult<Void> updateUserPass(@PathVariable("userId") Long userId,
			@RequestBody SysUserPassDTO sysUserPassDTO) {
		String pass = sysUserPassDTO.getPass();
		if (!pass.equals(sysUserPassDTO.getConfirmPass())) {
			return ApiResult.failed(SystemResultCode.BAD_REQUEST, "两次密码输入不一致!");
		}

		// 解密明文密码
		String rawPassword = this.passwordHelper.decodeAes(pass);
		// 密码规则校验
		if (this.passwordHelper.validateRule(rawPassword)) {
			return this.sysUserService.updatePassword(userId, rawPassword) ? ApiResult.ok()
					: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "修改用户密码失败！");
		}
		else {
			return ApiResult.failed(SystemResultCode.BAD_REQUEST, "密码格式不符合规则!");
		}
	}

	/**
	 * 批量修改用户状态
	 */
	@PutMapping("/status")
	@UpdateOperationLogging(msg = "批量修改用户状态")
	@Authorize("hasPermission('system:user:edit')")
	@Operation(summary = "批量修改用户状态", description = "批量修改用户状态")
	public ApiResult<Void> updateUserStatus(@NotEmpty(message = "用户ID不能为空") @RequestBody List<Long> userIds,
			@NotNull(message = "用户状态不能为空") @RequestParam("status") Integer status) {

		if (!SysUserConst.Status.NORMAL.getValue().equals(status)
				&& !SysUserConst.Status.LOCKED.getValue().equals(status)) {
			throw new ValidationException("不支持的用户状态！");
		}
		return this.sysUserService.updateUserStatusBatch(userIds, status) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "批量修改用户状态！");
	}

	@UpdateOperationLogging(msg = "修改系统用户头像")
	@Authorize("hasPermission('system:user:edit')")
	@PostMapping("/avatar")
	@Operation(summary = "修改系统用户头像", description = "修改系统用户头像")
	public ApiResult<String> updateAvatar(@RequestParam("file") MultipartFile file,
			@RequestParam("userId") Long userId) {
		String objectName;
		try {
			objectName = this.sysUserService.updateAvatar(file, userId);
		}
		catch (IOException e) {
			log.error("修改系统用户头像异常", e);
			return ApiResult.failed(BaseResultCode.FILE_UPLOAD_ERROR);
		}
		return ApiResult.ok(objectName);
	}

}
