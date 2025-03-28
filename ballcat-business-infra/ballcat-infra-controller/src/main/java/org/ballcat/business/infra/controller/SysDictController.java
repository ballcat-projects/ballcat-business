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

import java.util.List;
import java.util.Map;

import javax.validation.groups.Default;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.infra.manager.SysDictManager;
import org.ballcat.business.infra.model.dto.SysDictItemDTO;
import org.ballcat.business.infra.model.entity.SysDict;
import org.ballcat.business.infra.model.qo.SysDictQO;
import org.ballcat.business.infra.model.vo.DictDataVO;
import org.ballcat.business.infra.model.vo.SysDictItemPageVO;
import org.ballcat.business.infra.model.vo.SysDictPageVO;
import org.ballcat.common.core.validation.group.CreateGroup;
import org.ballcat.common.core.validation.group.UpdateGroup;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.ApiResult;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.log.operation.annotation.OperationLog;
import org.ballcat.security.annotation.Authorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典表
 *
 * @author hccake 2020-03-26 18:40:20
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/dict")
@Tag(name = "字典表管理")
public class SysDictController {

	private final SysDictManager sysDictManager;

	/**
	 * 通过字典标识查找对应字典项
	 * @param dictCodes 字典标识列表
	 * @return 同类型字典
	 */
	@GetMapping("/data")
	public ApiResult<List<DictDataVO>> getDictData(@RequestParam("dictCodes") String[] dictCodes) {
		return ApiResult.ok(this.sysDictManager.queryDictDataAndHashVO(dictCodes));
	}

	/**
	 * 通过字典标识查找对应字典项
	 * @param dictHashCode 字典标识
	 * @return 同类型字典
	 */
	@PostMapping("/invalid-hash")
	public ApiResult<List<String>> invalidDictHash(@RequestBody Map<String, String> dictHashCode) {
		return ApiResult.ok(this.sysDictManager.invalidDictHash(dictHashCode));
	}

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param sysDictQO 字典查询参数
	 * @return ApiResult<PageResult < SysDictVO>>
	 */
	@GetMapping("/page")
	@Authorize("hasPermission('system:dict:read')")
	@Operation(summary = "分页查询", description = "分页查询")
	public ApiResult<PageResult<SysDictPageVO>> getSysDictPage(@Validated PageParam pageParam, SysDictQO sysDictQO) {
		return ApiResult.ok(this.sysDictManager.dictPage(pageParam, sysDictQO));
	}

	/**
	 * 新增字典表
	 * @param sysDict 字典表
	 * @return ApiResult
	 */
	@OperationLog(bizType = "dict", subType = "dict.create", bizNo = "#{#sysDict.code}",
			successMessage = "用户创建了字典 #{#sysDict.code}")
	@PostMapping
	@Authorize("hasPermission('system:dict:add')")
	@Operation(summary = "新增字典表", description = "新增字典表")
	public ApiResult<Void> save(@RequestBody SysDict sysDict) {
		return this.sysDictManager.dictSave(sysDict) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "新增字典表失败");
	}

	/**
	 * 修改字典表
	 * @param sysDict 字典表
	 * @return ApiResult
	 */
	@OperationLog(bizType = "dict", subType = "dict.update", bizNo = "#{#sysDict.code}",
			successMessage = "用户修改了字典 #{#sysDict.code}")
	@PutMapping
	@Authorize("hasPermission('system:dict:edit')")
	@Operation(summary = "修改字典表", description = "修改字典表")
	public ApiResult<Void> updateById(@RequestBody SysDict sysDict) {
		return this.sysDictManager.updateDictById(sysDict) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "修改字典表失败");
	}

	/**
	 * 通过id删除字典表
	 * @param id id
	 * @return ApiResult
	 */
	@OperationLog(bizType = "dict", subType = "dict.delete", bizNo = "#{#sysDict.code}",
			successMessage = "用户删除了字典 #{#sysDict.code}")
	@DeleteMapping("/{id}")
	@Authorize("hasPermission('system:dict:del')")
	@Operation(summary = "通过id删除字典表", description = "通过id删除字典表")
	public ApiResult<Void> removeById(@PathVariable("id") Long id) {
		this.sysDictManager.removeDictById(id);
		return ApiResult.ok();
	}

	/**
	 * 通过id刷新hash值
	 * @param id id
	 * @return ApiResult
	 */
	@OperationLog(bizType = "dict", subType = "dict.refresh", bizNo = "#{#id}",
			successMessage = "用户删除了字典Hash, id: #{#id}")
	@PatchMapping("/refresh/{id}")
	@Authorize("hasPermission('system:dict:edit')")
	@Operation(summary = "通过id刷新hash值", description = "通过id刷新hash值")
	public ApiResult<Void> refreshDictHashById(@PathVariable("id") Long id) {
		this.sysDictManager.refreshDictHashById(id);
		return ApiResult.ok();
	}

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param dictCode 字典标识
	 * @return ApiResult
	 */
	@GetMapping("/item/page")
	@Authorize("hasPermission('system:dict:read')")
	@Operation(summary = "分页查询", description = "分页查询")
	public ApiResult<PageResult<SysDictItemPageVO>> getSysDictItemPage(PageParam pageParam,
			@RequestParam("dictCode") String dictCode) {
		return ApiResult.ok(this.sysDictManager.dictItemPage(pageParam, dictCode));
	}

	/**
	 * 新增字典项
	 * @param sysDictItemDTO 字典项
	 * @return ApiResult
	 */
	@OperationLog(bizType = "dict", subType = "dict.item.create", bizNo = "#{#sysDictItemDTO.id}",
			successMessage = "用户创建了 #{#sysDictItemDTO.dictCode} 下的字典项, #{#sysDictItemDTO.id}")
	@PostMapping("item")
	@Authorize("hasPermission('system:dict:add')")
	@Operation(summary = "新增字典项", description = "新增字典项")
	public ApiResult<Void> saveItem(
			@Validated({ Default.class, CreateGroup.class }) @RequestBody SysDictItemDTO sysDictItemDTO) {
		return this.sysDictManager.saveDictItem(sysDictItemDTO) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "新增字典项失败");
	}

	/**
	 * 修改字典项
	 * @param sysDictItemDTO 字典项
	 * @return ApiResult
	 */
	@OperationLog(bizType = "dict", subType = "dict.item.update", bizNo = "#{#sysDictItemDTO.id}",
			successMessage = "用户修改了 #{#sysDictItemDTO.dictCode} 下的字典项, #{#sysDictItemDTO.id}")
	@PutMapping("item")
	@Authorize("hasPermission('system:dict:edit')")
	@Operation(summary = "修改字典项", description = "修改字典项")
	public ApiResult<Void> updateItemById(
			@Validated({ Default.class, UpdateGroup.class }) @RequestBody SysDictItemDTO sysDictItemDTO) {
		return this.sysDictManager.updateDictItemById(sysDictItemDTO) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "修改字典项失败");
	}

	/**
	 * 通过id删除字典项
	 * @param id id
	 * @return ApiResult
	 */
	@OperationLog(bizType = "dict", subType = "dict.item.delete", bizNo = "#{#id}",
			successMessage = "用户删除了字典项： #{#sysDictItemDTO.id}")
	@DeleteMapping("/item/{id}")
	@Authorize("hasPermission('system:dict:del')")
	@Operation(summary = "通过id删除字典项", description = "通过id删除字典项")
	public ApiResult<Void> removeItemById(@PathVariable("id") Long id) {
		return this.sysDictManager.removeDictItemById(id) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "通过id删除字典项失败");
	}

	/**
	 * 通过id修改字典项状态
	 * @param id id
	 * @return ApiResult
	 */
	@OperationLog(bizType = "dict", subType = "dict.item.update-status", bizNo = "#{#id}",
			successMessage = "用户修改了字典项 #{#id} 的状态为 #{#status}")
	@PatchMapping("/item/{id}")
	@Authorize("hasPermission('system:dict:edit')")
	@Operation(summary = "通过id修改字典项状态", description = "通过id修改字典项状态")
	public ApiResult<Void> updateDictItemStatusById(@PathVariable("id") Long id,
			@RequestParam("status") Integer status) {
		this.sysDictManager.updateDictItemStatusById(id, status);
		return ApiResult.ok();
	}

}
