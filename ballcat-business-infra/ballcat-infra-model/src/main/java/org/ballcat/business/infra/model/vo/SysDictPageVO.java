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

package org.ballcat.business.infra.model.vo;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典表
 *
 * @author hccake 2020-03-26 18:40:20
 */
@Data
@Schema(title = "字典表")
public class SysDictPageVO {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@Schema(title = "编号")
	private Long id;

	/**
	 * 标识
	 */
	@Schema(title = "标识")
	private String code;

	/**
	 * 名称
	 */
	@Schema(title = "名称")
	private String title;

	/**
	 * Hash值
	 */
	@Schema(title = "Hash值")
	private String hashCode;

	/**
	 * 备注
	 */
	@Schema(title = "备注")
	private String remarks;

	/**
	 * 数据类型
	 */
	@Schema(title = "数据类型", description = "1:Number 2:String 3:Boolean")
	private Integer valueType;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(title = "更新时间")
	private LocalDateTime updateTime;

}
