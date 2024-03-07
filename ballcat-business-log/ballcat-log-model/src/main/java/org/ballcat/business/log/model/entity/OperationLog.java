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
 * 操作日志
 *
 * @author hccake 2019-10-15 20:42:32
 */
@Data
@TableName("log_operation_log")
@Accessors(chain = true)
@Schema(title = "操作日志")
public class OperationLog {

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
	 * 日志消息
	 */
	@Schema(title = "日志消息")
	private String msg;

	/**
	 * 访问IP地址
	 */
	@Schema(title = "访问IP地址")
	private String ip;

	/**
	 * 用户代理
	 */
	@Schema(title = "用户代理")
	private String userAgent;

	/**
	 * 请求URI
	 */
	@Schema(title = "请求URI")
	private String uri;

	/**
	 * 请求方法
	 */
	@Schema(title = "请求方法")
	private String method;

	/**
	 * 操作提交的数据
	 */
	@Schema(title = "操作提交的数据")
	private String params;

	/**
	 * 操作状态
	 */
	@Schema(title = "操作状态")
	private Integer status;

	/**
	 * 操作类型
	 */
	@Schema(title = "操作类型")
	private Integer type;

	/**
	 * 执行时长
	 */
	@Schema(title = "执行时长")
	private Long time;

	/**
	 * 操作结果
	 */
	@Schema(title = "操作结果")
	private String result;

	/**
	 * 创建者
	 */
	@Schema(title = "创建者")
	private String operator;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private LocalDateTime createTime;

}
