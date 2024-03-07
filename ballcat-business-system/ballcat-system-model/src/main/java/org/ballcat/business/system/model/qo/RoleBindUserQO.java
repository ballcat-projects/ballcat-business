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

package org.ballcat.business.system.model.qo;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * 角色绑定用户查询对象
 *
 * @author Hccake
 */
@Data
@Schema(title = "角色绑定用户查询对象")
@ParameterObject
public class RoleBindUserQO {

	@NotNull(message = "角色标识不能为空！")
	@Parameter(description = "角色标识")
	private String roleCode;

	@Parameter(description = "用户ID")
	private Long userId;

	@Parameter(description = "用户名")
	private String username;

	@Parameter(description = "组织ID")
	private Long organizationId;

}
