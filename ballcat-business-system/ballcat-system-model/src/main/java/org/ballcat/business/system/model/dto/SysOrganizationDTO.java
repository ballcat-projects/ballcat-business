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

package org.ballcat.business.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 组织架构
 *
 * @author hccake 2020-09-23 20:39:40
 */
@Data
@Schema(title = "组织架构DTO")
public class SysOrganizationDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Schema(title = "ID")
	private Long id;

	/**
	 * 组织名称
	 */
	@Schema(title = "组织名称")
	private String name;

	/**
	 * 父级ID
	 */
	@Schema(title = "父级ID")
	private Long parentId;

	/**
	 * 排序字段，由小到大
	 */
	@Schema(title = "排序字段，由小到大")
	private Integer sort;

	/**
	 * 备注
	 */
	@Schema(title = "备注")
	private String remarks;

}
