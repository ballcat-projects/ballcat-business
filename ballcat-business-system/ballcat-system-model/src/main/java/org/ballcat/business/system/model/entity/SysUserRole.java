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

package org.ballcat.business.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.mybatisplus.alias.TableAlias;

/**
 * 用户角色表
 *
 * @author ballcat code generator 2019-10-14 17:42:23
 */
@Data
@TableAlias("ur")
@TableName("sys_user_role")
@Schema(title = "用户角色")
public class SysUserRole {

	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;

	/**
	 * 用户ID
	 */
	@Schema(title = "用户id")
	private Long userId;

	/**
	 * 角色Code
	 */
	@Schema(title = "角色Code")
	private String roleCode;

}
