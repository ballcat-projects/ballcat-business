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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.desensitize.enums.RegexDesensitizationTypeEnum;
import org.ballcat.desensitize.json.annotation.JsonRegexDesensitize;

/**
 * 用户密码传输DTO，字段序列化时忽略，防止记录
 *
 * @author Hccake 2021/1/22
 */
@Data
@Schema(title = "系统用户密码传输实体")
public class SysUserPassDTO {

	/**
	 * 前端传入密码
	 */
	@NotBlank(message = "The password cannot be empty!")
	@JsonRegexDesensitize(type = RegexDesensitizationTypeEnum.ENCRYPTED_PASSWORD)
	@Schema(title = "前端输入密码")
	private String pass;

	/**
	 * 前端确认密码
	 */
	@NotBlank(message = "The confirm password cannot be empty!")
	@JsonRegexDesensitize(type = RegexDesensitizationTypeEnum.ENCRYPTED_PASSWORD)
	@Schema(title = "前端确认密码")
	private String confirmPass;

}
