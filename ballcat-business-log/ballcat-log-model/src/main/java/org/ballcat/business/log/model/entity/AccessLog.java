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

package org.ballcat.business.log.model.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 后台访问日志
 *
 * @author hccake 2019-10-16 16:09:25
 */
@Data
@TableName("log_access_log")
@Accessors(chain = true)
@Schema(title = "后台访问日志")
public class AccessLog {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	@Schema(title = "编号")
	private Long id;

	/**
	 * 追踪ID
	 */
	@Schema(title = "追踪ID")
	private String traceId;

	/**
	 * 用户ID
	 */
	@Schema(title = "用户ID")
	private Long userId;

	/**
	 * 用户名
	 */
	@Schema(title = "用户名")
	private String username;

	/**
	 * 客户端IP地址
	 */
	@Schema(title = "客户端IP地址")
	private String clientIp;

	/**
	 * 用户代理
	 */
	@Schema(title = "用户代理")
	private String userAgent;

	/**
	 * 请求URI
	 */
	@Schema(title = "请求URI")
	private String requestUri;

	/**
	 * 请求映射地址
	 */
	@Schema(title = "请求映射地址")
	private String matchingPattern;

	/**
	 * 请求方法
	 */
	@Schema(title = "请求方法")
	private String requestMethod;

	/**
	 * 查询参数
	 */
	@Schema(title = "查询参数")
	private String queryString;

	/**
	 * 请求体
	 */
	@Schema(title = "请求体")
	private String requestBody;

	/**
	 * 响应状态码
	 */
	@Schema(title = "响应状态码")
	private Integer responseStatus;

	/**
	 * 响应体
	 */
	@Schema(title = "响应体")
	private String responseBody;

	/**
	 * 错误消息
	 */
	@Schema(title = "错误消息")
	private String errorMessage;

	/**
	 * 执行时长
	 */
	@Schema(title = "执行时长")
	private Long executionTime;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private LocalDateTime createTime;

}
