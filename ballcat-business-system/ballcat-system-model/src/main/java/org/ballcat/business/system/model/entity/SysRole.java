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

import java.util.Objects;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ballcat.common.model.entity.LogicDeletedBaseEntity;

/**
 * 角色
 *
 * @author ballcat code generator 2019-10-14 17:42:23
 */
@Getter
@Setter
@ToString
@TableName("sys_role")
@Schema(title = "角色")
public class SysRole extends LogicDeletedBaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@Schema(title = "角色编号")
	private Long id;

	@NotBlank(message = "角色名称不能为空")
	@Schema(title = "角色名称")
	private String name;

	@NotBlank(message = "角色标识不能为空")
	@Schema(title = "角色标识")
	private String code;

	@Schema(title = "角色类型，1：系统角色 2：业务角色")
	private Integer type;

	@Schema(title = "数据权限")
	private Integer scopeType;

	@Schema(title = "数据范围资源，当数据范围类型为自定义时使用")
	private String scopeResources;

	@Schema(title = "角色备注")
	private String remarks;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SysRole sysRole = (SysRole) o;
		return this.code.equals(sysRole.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.code);
	}

}
