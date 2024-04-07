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

package org.ballcat.business.notify.controller;

import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.notify.model.dto.AnnouncementDTO;
import org.ballcat.business.notify.model.qo.AnnouncementQO;
import org.ballcat.business.notify.model.vo.AnnouncementPageVO;
import org.ballcat.business.notify.service.AnnouncementService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.ApiResult;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.log.operation.annotation.CreateOperationLogging;
import org.ballcat.log.operation.annotation.DeleteOperationLogging;
import org.ballcat.log.operation.annotation.UpdateOperationLogging;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 公告信息
 *
 * @author hccake 2020-12-15 17:01:15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/announcement")
@Tag(name = "公告信息管理")
public class AnnouncementController {

	private final AnnouncementService announcementService;

	/**
	 * 分页查询
	 * @param pageParam 分页对象
	 * @param announcementQO 公告信息查询对象
	 * @return ApiResult 通用返回体
	 */
	@GetMapping("/page")
	@Authorize("hasPermission('notify:announcement:read')")
	@Operation(summary = "分页查询", description = "分页查询")
	public ApiResult<PageResult<AnnouncementPageVO>> getAnnouncementPage(@Validated PageParam pageParam,
			AnnouncementQO announcementQO) {
		return ApiResult.ok(this.announcementService.queryPage(pageParam, announcementQO));
	}

	/**
	 * 新增公告信息
	 * @param announcementDTO 公告信息
	 * @return ApiResult 通用返回体
	 */
	@CreateOperationLogging(msg = "新增公告信息")
	@PostMapping
	@Authorize("hasPermission('notify:announcement:add')")
	@Operation(summary = "新增公告信息", description = "新增公告信息")
	public ApiResult<Void> save(@Valid @RequestBody AnnouncementDTO announcementDTO) {
		return this.announcementService.addAnnouncement(announcementDTO) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "新增公告信息失败");
	}

	/**
	 * 修改公告信息
	 * @param announcementDTO 公告信息
	 * @return ApiResult 通用返回体
	 */
	@UpdateOperationLogging(msg = "修改公告信息")
	@PutMapping
	@Authorize("hasPermission('notify:announcement:edit')")
	@Operation(summary = "修改公告信息", description = "修改公告信息")
	public ApiResult<Void> updateById(@Valid @RequestBody AnnouncementDTO announcementDTO) {
		return this.announcementService.updateAnnouncement(announcementDTO) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "修改公告信息失败");
	}

	/**
	 * 通过id删除公告信息
	 * @param id id
	 * @return ApiResult 通用返回体
	 */
	@DeleteOperationLogging(msg = "通过id删除公告信息")
	@DeleteMapping("/{id}")
	@Authorize("hasPermission('notify:announcement:del')")
	@Operation(summary = "通过id删除公告信息", description = "通过id删除公告信息")
	public ApiResult<Void> removeById(@PathVariable("id") Long id) {
		return this.announcementService.removeById(id) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "通过id删除公告信息失败");
	}

	/**
	 * 发布公告信息
	 * @return ApiResult 通用返回体
	 */
	@UpdateOperationLogging(msg = "发布公告信息")
	@PatchMapping("/publish/{announcementId}")
	@Authorize("hasPermission('notify:announcement:edit')")
	@Operation(summary = "发布公告信息", description = "发布公告信息")
	public ApiResult<Void> enableAnnouncement(@PathVariable("announcementId") Long announcementId) {
		return this.announcementService.publish(announcementId) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "发布公告信息失败");
	}

	/**
	 * 关闭公告信息
	 * @return ApiResult 通用返回体
	 */
	@UpdateOperationLogging(msg = "关闭公告信息")
	@PatchMapping("/close/{announcementId}")
	@Authorize("hasPermission('notify:announcement:edit')")
	@Operation(summary = "关闭公告信息", description = "关闭公告信息")
	public ApiResult<Void> disableAnnouncement(@PathVariable("announcementId") Long announcementId) {
		return this.announcementService.close(announcementId) ? ApiResult.ok()
				: ApiResult.failed(BaseResultCode.UPDATE_DATABASE_ERROR, "关闭公告信息失败");
	}

	@UpdateOperationLogging(msg = "公告内容图片上传", recordParams = false)
	@Authorize("hasPermission('notify:announcement:edit')")
	@PostMapping("/image")
	@Operation(summary = "公告内容图片上传", description = "公告内容图片上传")
	public ApiResult<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
		List<String> objectNames = this.announcementService.uploadImages(files);
		return ApiResult.ok(objectNames);
	}

}
