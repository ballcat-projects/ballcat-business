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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.notify.model.qo.UserAnnouncementQO;
import org.ballcat.business.notify.model.vo.UserAnnouncementPageVO;
import org.ballcat.business.notify.model.vo.UserAnnouncementVO;
import org.ballcat.business.notify.service.UserAnnouncementService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.ApiResult;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户公告表
 *
 * @author hccake 2020-12-25 08:04:53
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/user-announcement")
@Tag(name = "用户公告表管理")
public class UserAnnouncementController {

	private final UserAnnouncementService userAnnouncementService;

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param userAnnouncementQO 用户公告表查询对象
	 * @return ApiResult 通用返回体
	 */
	@GetMapping("/page")
	@Operation(summary = "分页查询", description = "分页查询")
	public ApiResult<PageResult<UserAnnouncementPageVO>> getUserAnnouncementPage(@Validated PageParam pageParam,
			UserAnnouncementQO userAnnouncementQO) {
		return ApiResult.ok(this.userAnnouncementService.queryPage(pageParam, userAnnouncementQO));
	}

	@GetMapping("/list")
	@Operation(summary = "用户公告信息", description = "用户公告信息")
	public ApiResult<List<UserAnnouncementVO>> getUserAnnouncements() {
		Long userId = this.principalAttributeAccessor.getUserId();
		return ApiResult.ok(this.userAnnouncementService.listActiveAnnouncements(userId));
	}

	@PatchMapping("/read/{announcementId}")
	@Operation(summary = "用户公告已读上报", description = "用户公告已读上报")
	public ApiResult<Void> readAnnouncement(@PathVariable("announcementId") Long announcementId) {
		Long userId = this.principalAttributeAccessor.getUserId();
		this.userAnnouncementService.readAnnouncement(userId, announcementId);
		return ApiResult.ok();
	}

}
