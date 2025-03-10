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

package org.ballcat.business.i18n.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.i18n.converter.I18nDataConverter;
import org.ballcat.business.i18n.model.dto.I18nDataCreateDTO;
import org.ballcat.business.i18n.model.dto.I18nDataDTO;
import org.ballcat.business.i18n.model.entity.I18nData;
import org.ballcat.business.i18n.model.qo.I18nDataQO;
import org.ballcat.business.i18n.model.vo.I18nDataExcelVO;
import org.ballcat.business.i18n.model.vo.I18nDataPageVO;
import org.ballcat.business.i18n.service.I18nDataService;
import org.ballcat.common.core.constant.enums.ImportModeEnum;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.ApiResult;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.common.model.result.SystemResultCode;
import org.ballcat.fastexcel.annotation.RequestExcel;
import org.ballcat.fastexcel.annotation.ResponseExcel;
import org.ballcat.fastexcel.domain.ErrorMessage;
import org.ballcat.log.operation.annotation.OperationLog;
import org.ballcat.security.annotation.Authorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
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
 * 国际化信息
 *
 * @author hccake 2021-08-06 10:48:25
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/i18n/i18n-data")
@Tag(name = "国际化信息管理")
public class I18nDataController {

	private final I18nDataService i18nDataService;

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param i18nDataQO 国际化信息查询对象
	 * @return ApiResult 通用返回体
	 */
	@GetMapping("/page")
	@Authorize("hasPermission('i18n:i18n-data:read')")
	@Operation(summary = "分页查询", description = "分页查询")
	public ApiResult<PageResult<I18nDataPageVO>> getI18nDataPage(@Validated PageParam pageParam,
			I18nDataQO i18nDataQO) {
		return ApiResult.ok(this.i18nDataService.queryPage(pageParam, i18nDataQO));
	}

	/**
	 * 查询指定国际化标识的所有数据
	 * @param code 国际化标识
	 * @return ApiResult 通用返回体
	 */
	@GetMapping("/list")
	@Authorize("hasPermission('i18n:i18n-data:read')")
	@Operation(summary = "查询指定国际化标识的所有数据", description = "查询指定国际化标识的所有数据")
	public ApiResult<List<I18nData>> listByCode(@RequestParam("code") String code) {
		return ApiResult.ok(this.i18nDataService.listByCode(code));
	}

	/**
	 * 新增国际化信息
	 * @param i18nDataCreateDTO 国际化信息
	 * @return ApiResult 通用返回体
	 */
	@OperationLog(bizType = "i18n", subType = "i18n.create", bizNo = "#{#p0.code}",
			successMessage = "用户创建了国际化信息: #{#p0.code}")
	@PostMapping
	@Authorize("hasPermission('i18n:i18n-data:add')")
	@Operation(summary = "新增国际化信息", description = "新增国际化信息")
	public ApiResult<Void> save(@Valid @RequestBody I18nDataCreateDTO i18nDataCreateDTO) {
		// 转换为实体类列表
		List<I18nData> list = new ArrayList<>();
		List<I18nDataCreateDTO.LanguageText> languageTexts = i18nDataCreateDTO.getLanguageTexts();
		for (I18nDataCreateDTO.LanguageText languageText : languageTexts) {
			I18nData i18nData = new I18nData();
			i18nData.setCode(i18nDataCreateDTO.getCode());
			i18nData.setRemarks(i18nDataCreateDTO.getRemarks());
			i18nData.setLanguageTag(languageText.getLanguageTag());
			i18nData.setMessage(languageText.getMessage());
			list.add(i18nData);
		}
		return this.i18nDataService.saveBatch(list) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "新增国际化信息失败");
	}

	/**
	 * 修改国际化信息
	 * @param i18nDataDTO 国际化信息
	 * @return ApiResult 通用返回体
	 */
	@OperationLog(bizType = "i18n", subType = "i18n.create", bizNo = "#{#p0.code}",
			successMessage = "用户修改了国际化信息: #{#p0.code}")
	@PutMapping
	@Authorize("hasPermission('i18n:i18n-data:edit')")
	@Operation(summary = "修改国际化信息", description = "修改国际化信息")
	public ApiResult<Void> updateById(@RequestBody I18nDataDTO i18nDataDTO) {
		return this.i18nDataService.updateByCodeAndLanguageTag(i18nDataDTO) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "修改国际化信息失败");
	}

	/**
	 * 通过id删除国际化信息
	 * @param code code 唯一标识
	 * @param languageTag 语言标签
	 * @return ApiResult 通用返回体
	 */
	@OperationLog(bizType = "i18n", subType = "i18n.create", bizNo = "#{#code}",
			successMessage = "用户删除了国际化信息: #{#code}")
	@DeleteMapping
	@Authorize("hasPermission('i18n:i18n-data:del')")
	@Operation(summary = "通过id删除国际化信息", description = "通过id删除国际化信息")
	public ApiResult<Void> removeById(@RequestParam("code") String code,
			@RequestParam("languageTag") String languageTag) {
		return this.i18nDataService.removeByCodeAndLanguageTag(code, languageTag) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "通过id删除国际化信息失败");
	}

	/**
	 * 导入国际化信息
	 * @return ApiResult 通用返回体
	 */
	@PostMapping("/import")
	@Authorize("hasPermission('i18n:i18n-data:import')")
	@Operation(summary = "导入国际化信息", description = "导入国际化信息")
	public ApiResult<List<I18nData>> importI18nData(@RequestExcel List<I18nDataExcelVO> excelVos,
			@RequestParam("importMode") ImportModeEnum importModeEnum, BindingResult bindingResult) {

		// 通用校验获取失败的数据
		List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
		if (!CollectionUtils.isEmpty(errorMessageList)) {
			StringBuilder sb = new StringBuilder();
			for (ErrorMessage errorMessage : errorMessageList) {
				sb.append(errorMessage.getLineNum())
					.append("行校验失败:")
					.append(String.join(",", errorMessage.getErrors()))
					.append(";");
			}
			return ApiResult.failed(SystemResultCode.BAD_REQUEST, sb.toString());
		}

		if (CollectionUtils.isEmpty(excelVos)) {
			return ApiResult.ok();
		}

		// 转换结构
		List<I18nData> list = excelVos.stream()
			.map(I18nDataConverter.INSTANCE::excelVoToPo)
			.collect(Collectors.toList());

		// 跳过已有数据，返回已有数据列表
		if (importModeEnum == ImportModeEnum.SKIP_EXISTING) {
			List<I18nData> existsList = this.i18nDataService.saveWhenNotExist(list);
			return ApiResult.ok(existsList);
		}

		// 覆盖已有数据
		if (importModeEnum == ImportModeEnum.OVERWRITE_EXISTING) {
			this.i18nDataService.saveOrUpdate(list);
		}

		return ApiResult.ok();
	}

	/**
	 * 导出国际化信息
	 * @param i18nDataQO 国际化信息查询对象
	 * @return List<I18nDataExcelVO>
	 */
	@ResponseExcel(name = "国际化信息", i18nHeader = true)
	@GetMapping("/export")
	@Authorize("hasPermission('i18n:i18n-data:export')")
	@Operation(summary = "导出国际化信息", description = "导出国际化信息")
	public List<I18nDataExcelVO> exportI18nData(I18nDataQO i18nDataQO) {
		List<I18nData> list = this.i18nDataService.queryList(i18nDataQO);
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		// 转换为 excel vo 对象
		return list.stream().map(I18nDataConverter.INSTANCE::poToExcelVo).collect(Collectors.toList());
	}

	/**
	 * 国际化 excel 模板
	 * @return List<I18nDataExcelVO>
	 */
	@ResponseExcel(name = "国际化信息模板", i18nHeader = true)
	@GetMapping("/excel-template")
	@Authorize("hasPermission('i18n:i18n-data:import')")
	@Operation(summary = "国际化信息 Excel 模板", description = "国际化信息 Excel 模板")
	public List<I18nDataExcelVO> excelTemplate() {
		List<I18nDataExcelVO> list = new ArrayList<>();
		list.add(new I18nDataExcelVO());
		return list;
	}

}
