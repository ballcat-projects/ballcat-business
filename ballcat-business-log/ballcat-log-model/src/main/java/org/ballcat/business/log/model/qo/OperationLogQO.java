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
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 操作日志查询对象
 *
 * @author hccake
 * @date 2019-10-15 20:42:32
 */
@Data
@Schema(title = "操作日志查询对象")
@ParameterObject
public class OperationLogQO {

	/**
	 * 追踪ID
	 */
	@Parameter(description = "追踪ID")
	private String traceId;

	/**
	 * 用户ID
	 */
	@Parameter(description = "用户ID")
	private Long userId;

	/**
	 * 日志消息
	 */
	@Parameter(description = "日志消息")
	private String msg;

	/**
	 * 访问IP地址
	 */
	@Parameter(description = "访问IP地址")
	private String ip;

	/**
	 * 请求URI
	 */
	@Parameter(description = "请求URI")
	private String uri;

	/**
	 * 操作状态
	 */
	@Parameter(description = "操作状态")
	private Integer status;

	/**
	 * 操作类型
	 */
	@Parameter(description = "操作类型")
	private Integer type;

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
