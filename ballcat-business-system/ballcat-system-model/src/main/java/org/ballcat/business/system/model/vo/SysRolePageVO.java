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

package org.ballcat.business.system.model.vo;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author ballcat
 * @since 2017-10-29
 */
@Data
@Schema(title = "角色")
public class SysRolePageVO {

	private static final long serialVersionUID = 1L;

	@Schema(title = "角色编号")
	private Long id;

	@Schema(title = "角色名称")
	private String name;

	@Schema(title = "角色标识")
	private String code;

	@Schema(title = "角色类型，1：系统角色 2：业务角色")
	private Integer type;

	@Schema(title = "数据权限类型")
	private Integer scopeType;

	@Schema(title = "数据范围资源，当数据范围类型为自定义时使用")
	private String scopeResources;

	@Schema(title = "角色备注")
	private String remarks;

	@Schema(title = "创建时间")
	private LocalDateTime createTime;

	@Schema(title = "更新时间")
	private LocalDateTime updateTime;

}
