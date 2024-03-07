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
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.system.converter.SysOrganizationConverter;
import org.ballcat.business.system.model.dto.SysOrganizationDTO;
import org.ballcat.business.system.model.entity.SysOrganization;
import org.ballcat.business.system.model.qo.SysOrganizationQO;
import org.ballcat.business.system.model.vo.SysOrganizationTree;
import org.ballcat.business.system.model.vo.SysOrganizationVO;
import org.ballcat.business.system.service.SysOrganizationService;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.common.model.result.R;
import org.ballcat.log.operation.annotation.CreateOperationLogging;
import org.ballcat.log.operation.annotation.DeleteOperationLogging;
import org.ballcat.log.operation.annotation.UpdateOperationLogging;
import org.ballcat.security.annotation.Authorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织架构
 *
 * @author hccake 2020-09-23 12:09:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/organization")
@Tag(name = "组织架构管理")
public class SysOrganizationController {

	private final SysOrganizationService sysOrganizationService;

	/**
	 * 组织架构列表查询
	 * @return R 通用返回体
	 */
	@GetMapping("/list")
	@Authorize("hasPermission('system:organization:read')")
	@Operation(summary = "组织架构列表查询")
	public R<List<SysOrganizationVO>> listOrganization() {
		List<SysOrganization> list = this.sysOrganizationService.list();
		if (CollectionUtils.isEmpty(list)) {
			return R.ok(new ArrayList<>());
		}
		List<SysOrganizationVO> voList = list.stream()
			.sorted(Comparator.comparingInt(SysOrganization::getSort))
			.map(SysOrganizationConverter.INSTANCE::poToVo)
			.collect(Collectors.toList());
		return R.ok(voList);
	}

	/**
	 * 组织架构树查询
	 * @param qo 组织机构查询条件
	 * @return R 通用返回体
	 */
	@GetMapping("/tree")
	@Authorize("hasPermission('system:organization:read')")
	@Operation(summary = "组织架构树查询")
	public R<List<SysOrganizationTree>> getOrganizationTree(SysOrganizationQO qo) {
		return R.ok(this.sysOrganizationService.listTree(qo));
	}

	/**
	 * 新增组织架构
	 * @param sysOrganizationDTO 组织机构DTO
	 * @return R 通用返回体
	 */
	@CreateOperationLogging(msg = "新增组织架构")
	@PostMapping
	@Authorize("hasPermission('system:organization:add')")
	@Operation(summary = "新增组织架构")
	public R<Void> save(@RequestBody SysOrganizationDTO sysOrganizationDTO) {
		return this.sysOrganizationService.create(sysOrganizationDTO) ? R.ok()
				: R.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "新增组织架构失败");
	}

	/**
	 * 修改组织架构
	 * @param sysOrganizationDTO 组织机构DTO
	 * @return R 通用返回体
	 */
	@UpdateOperationLogging(msg = "修改组织架构")
	@PutMapping
	@Authorize("hasPermission('system:organization:edit')")
	@Operation(summary = "修改组织架构")
	public R<Void> updateById(@RequestBody SysOrganizationDTO sysOrganizationDTO) {
		return this.sysOrganizationService.update(sysOrganizationDTO) ? R.ok()
				: R.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "修改组织架构失败");
	}

	/**
	 * 通过id删除组织架构
	 * @param id id
	 * @return R 通用返回体
	 */
	@DeleteOperationLogging(msg = "通过id删除组织架构")
	@DeleteMapping("/{id}")
	@Authorize("hasPermission('system:organization:del')")
	@Operation(summary = "通过id删除组织架构")
	public R<Void> removeById(@PathVariable("id") Long id) {
		return this.sysOrganizationService.removeById(id) ? R.ok()
				: R.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "通过id删除组织架构失败");
	}

	/**
	 * 校正组织机构层级和深度
	 * @return R 通用返回体
	 */
	@UpdateOperationLogging(msg = "校正组织机构层级和深度")
	@PatchMapping("/revised")
	@Authorize("hasPermission('system:organization:revised')")
	@Operation(summary = "校正组织机构层级和深度")
	public R<Void> revisedHierarchyAndPath() {
		return this.sysOrganizationService.revisedHierarchyAndPath() ? R.ok()
				: R.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "校正组织机构层级和深度失败");
	}

}
