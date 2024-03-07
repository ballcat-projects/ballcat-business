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
 * 后台访问日志
 *
 * @author hccake 2019-10-16 16:09:25
 */
@Data
@Schema(title = "后台访问日志查询对象")
@ParameterObject
public class AccessLogQO {

	private static final long serialVersionUID = 1L;

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
	 * 客户端IP地址
	 */
	@Schema(title = "客户端IP地址")
	private String clientIp;

	/**
	 * 请求URI
	 */
	@Schema(title = "请求URI")
	private String requestUri;

	/**
	 * 请求映射地址
	 */
	@Parameter(description = "请求映射地址")
	private String matchingPattern;

	/**
	 * 响应状态码
	 */
	@Schema(title = "响应状态码")
	private Integer responseStatus;

	/**
	 * 访问时间区间（开始时间）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Parameter(description = "开始时间（登录时间区间）")
	private LocalDateTime startTime;

	/**
	 * 访问时间区间（结束时间）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Parameter(description = "结束时间（登录时间区间）")
	private LocalDateTime endTime;

}
