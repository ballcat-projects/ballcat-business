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

package org.ballcat.business.infra.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.infra.model.entity.SysConfig;
import org.ballcat.business.infra.model.qo.SysConfigQO;
import org.ballcat.business.infra.model.vo.SysConfigPageVO;
import org.ballcat.business.infra.service.SysConfigService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.ApiResult;
import org.ballcat.log.operation.annotation.OperationLog;
import org.ballcat.security.annotation.Authorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统配置
 *
 * @author ballcat code generator 2019-10-14 17:42:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/config")
@Tag(name = "系统配置")
public class SysConfigController {

	private final SysConfigService sysConfigService;

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param sysConfigQO 系统配置
	 * @return ApiResult<PageResult < SysConfigVO>>
	 */
	@GetMapping("/page")
	@Authorize("hasPermission('system:config:read')")
	@Operation(summary = "分页查询", description = "分页查询")
	public ApiResult<PageResult<SysConfigPageVO>> getSysConfigPage(@Validated PageParam pageParam,
			SysConfigQO sysConfigQO) {
		return ApiResult.ok(this.sysConfigService.queryPage(pageParam, sysConfigQO));
	}

	/**
	 * 新增系统配置
	 * @param sysConfig 系统配置
	 * @return ApiResult
	 */
	@OperationLog(bizType = "config", subType = "config.create", bizNo = "#{#p0.confKey}",
			successMessage = "用户创建了系统配置 #{#sysConfig.name}，key: #{#sysConfig.confKey}， value: #{#sysConfig.confValue}")
	@PostMapping
	@Authorize("hasPermission('system:config:add')")
	@Operation(summary = "新增系统配置", description = "新增系统配置")
	public ApiResult<Boolean> save(@RequestBody SysConfig sysConfig) {
		return ApiResult.ok(this.sysConfigService.save(sysConfig));
	}

	/**
	 * 修改系统配置
	 * @param sysConfig 系统配置
	 * @return ApiResult
	 */
	@OperationLog(bizType = "config", subType = "config.update", bizNo = "#{#p0.confKey}",
			successMessage = "用户修改了系统配置 #{#sysConfig.name}，key: #{#sysConfig.confKey}， value: #{#sysConfig.confValue}")
	@PutMapping
	@Authorize("hasPermission('system:config:edit')")
	@Operation(summary = "修改系统配置")
	public ApiResult<Boolean> updateById(@RequestBody SysConfig sysConfig) {
		return ApiResult.ok(this.sysConfigService.updateByKey(sysConfig));
	}

	/**
	 * 删除系统配置
	 * @param confKey confKey
	 * @return ApiResult
	 */
	@OperationLog(bizType = "config", subType = "config.delete", bizNo = "#{#confKey}",
			successMessage = "用户删除了系统配置 key: #{#sysConfig.confKey}")
	@DeleteMapping
	@Authorize("hasPermission('system:config:del')")
	@Operation(summary = "删除系统配置")
	public ApiResult<Boolean> removeById(@RequestParam("confKey") String confKey) {
		return ApiResult.ok(this.sysConfigService.removeByKey(confKey));
	}

}
