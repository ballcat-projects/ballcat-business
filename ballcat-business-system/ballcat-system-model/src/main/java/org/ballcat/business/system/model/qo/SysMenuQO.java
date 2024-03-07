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
 * 菜单权限 查询对象
 *
 * @author hccake 2021-04-06 17:59:51
 */
@Data
@Schema(title = "菜单权限查询对象")
@ParameterObject
public class SysMenuQO {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@Parameter(description = "菜单ID")
	private Long id;

	/**
	 * 菜单名称
	 */
	@Parameter(description = "菜单名称")
	private String title;

	/**
	 * 授权标识
	 */
	@Parameter(description = "授权标识")
	private String permission;

	/**
	 * 路由地址
	 */
	@Parameter(description = "路由地址")
	private String path;

}
