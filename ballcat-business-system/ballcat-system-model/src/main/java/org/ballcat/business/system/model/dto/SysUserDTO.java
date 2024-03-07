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

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.common.core.validation.group.CreateGroup;
import org.ballcat.common.core.validation.group.UpdateGroup;
import org.ballcat.desensitize.enums.RegexDesensitizationTypeEnum;
import org.ballcat.desensitize.json.annotation.JsonRegexDesensitize;
import org.hibernate.validator.constraints.Range;

/**
 * 系统用户表
 *
 * @author ballcat code generator 2019-09-12 20:39:31
 */
@Data
@Schema(title = "系统用户DTO")
public class SysUserDTO {

	/**
	 * 主键id
	 */
	@NotNull(message = "userId {}", groups = UpdateGroup.class)
	@Schema(title = "主键id")
	private Long userId;

	/**
	 * 前端传入密码
	 */
	@NotEmpty(message = "pass {}", groups = CreateGroup.class)
	@JsonRegexDesensitize(type = RegexDesensitizationTypeEnum.ENCRYPTED_PASSWORD)
	@Schema(title = "前端传入密码")
	private String pass;

	/**
	 * 用户明文密码, 不参与前后端交互
	 */
	@JsonIgnore
	private String password;

	/**
	 * 登录账号
	 */
	@NotEmpty(message = "username {}")
	@Schema(title = "登录账号")
	private String username;

	/**
	 * 昵称
	 */
	@NotEmpty(message = "nickname {}")
	@Schema(title = "昵称")
	private String nickname;

	/**
	 * 头像
	 */
	@Schema(title = "头像")
	private String avatar;

	/**
	 * 性别(0-默认未知,1-男,2-女)
	 */
	@Schema(title = "性别(0-默认未知,1-男,2-女)")
	private Integer gender;

	/**
	 * 电子邮件
	 */
	@Schema(title = "电子邮件")
	private String email;

	/**
	 * 手机号
	 */
	@Schema(title = "手机号")
	private String phoneNumber;

	/**
	 * 状态(1-正常,2-冻结)
	 */
	@Range(message = "status {}", min = 0, max = 1)
	@Schema(title = "状态(1-正常,2-冻结)")
	private Integer status;

	/**
	 * 组织机构ID
	 */
	@Schema(title = "组织机构ID")
	private Long organizationId;

	/**
	 * 角色标识列表
	 */
	@Schema(title = "角色标识列表")
	private List<String> roleCodes;

}
