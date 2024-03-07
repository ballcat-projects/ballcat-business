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

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典项
 *
 * @author hccake 2020-03-26 18:40:20
 */
@Data
@Schema(title = "字典项VO")
public class DictItemVO {

	private static final long serialVersionUID = 1L;

	@Schema(title = "id")
	private Long id;

	/**
	 * 数据值
	 */
	@Schema(title = "数据值")
	private String value;

	/**
	 * 标签
	 */
	@Schema(title = "文本值")
	private String name;

	/**
	 * 状态
	 */
	@Schema(title = "状态", description = "1：启用 0：禁用")
	private Integer status;

	/**
	 * 附加属性值
	 */
	@Schema(title = "附加属性值")
	private Map<String, Object> attributes;

}
