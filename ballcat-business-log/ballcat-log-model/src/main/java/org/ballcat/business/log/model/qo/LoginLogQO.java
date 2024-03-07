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

package org.ballcat.business.log.model.qo;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.business.log.enums.LoginEventTypeEnum;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 登录日志 查询对象
 *
 * @author hccake 2020-09-16 20:21:10
 */
@Data
@Schema(title = "登录日志查询对象")
@ParameterObject
public class LoginLogQO {

	private static final long serialVersionUID = 1L;

	/**
	 * 追踪ID
	 */
	@Parameter(description = "追踪ID")
	private String traceId;

	/**
	 * 用户名
	 */
	@Parameter(description = "用户名")
	private String username;

	/**
	 * 操作信息
	 */
	@Parameter(description = "请求IP")
	private String ip;

	/**
	 * 状态
	 */
	@Parameter(description = "状态")
	private Integer status;

	/**
	 * 事件类型 登录/登出
	 *
	 * @see LoginEventTypeEnum
	 */
	@Parameter(description = "事件类型")
	private Integer eventType;

	/**
	 * 登录时间区间（开始时间）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Parameter(description = "开始时间（登录时间区间）")
	private LocalDateTime startTime;

	/**
	 * 登录时间区间（结束时间）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Parameter(description = "结束时间（登录时间区间）")
	private LocalDateTime endTime;

}
