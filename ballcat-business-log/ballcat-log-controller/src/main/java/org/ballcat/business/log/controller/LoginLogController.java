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

package org.ballcat.business.log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.log.model.qo.LoginLogQO;
import org.ballcat.business.log.model.vo.LoginLogPageVO;
import org.ballcat.business.log.service.LoginLogService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.ApiResult;
import org.ballcat.security.annotation.Authorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志
 *
 * @author hccake 2020-09-16 20:21:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/log/login-log")
@Tag(name = "登录日志管理")
public class LoginLogController {

	private final LoginLogService loginLogService;

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param loginLogQO 登录日志查询对象
	 * @return ApiResult 通用返回体
	 */
	@GetMapping("/page")
	@Authorize("hasPermission('log:login-log:read')")
	@Operation(summary = "分页查询", description = "分页查询")
	public ApiResult<PageResult<LoginLogPageVO>> getLoginLogPage(@Validated PageParam pageParam,
			LoginLogQO loginLogQO) {
		return ApiResult.ok(this.loginLogService.queryPage(pageParam, loginLogQO));
	}

}
