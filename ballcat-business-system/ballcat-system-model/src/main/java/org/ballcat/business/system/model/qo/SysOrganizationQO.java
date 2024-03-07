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

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * 组织架构 查询对象
 *
 * @author hccake 2020-09-23 12:09:43
 */
@Data
@Schema(title = "组织架构查询对象")
@ParameterObject
public class SysOrganizationQO {

	private static final long serialVersionUID = 1L;

	/**
	 * 组织名称
	 */
	@Parameter(description = "组织名称")
	private String name;

}
