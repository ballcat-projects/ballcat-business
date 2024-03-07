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

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色修改DTO
 *
 * @author Hccake 2020-07-06
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "角色修改DTO")
public class SysRoleUpdateDTO {

	private static final long serialVersionUID = 1L;

	@Schema(title = "角色编号")
	private Long id;

	@NotBlank(message = "角色名称不能为空")
	@Schema(title = "角色名称")
	private String name;

	@Schema(title = "角色备注")
	private String remarks;

	@Schema(title = "数据权限")
	private Integer scopeType;

	@Schema(title = "数据范围资源，当数据范围类型为自定义时使用")
	private String scopeResources;

}
