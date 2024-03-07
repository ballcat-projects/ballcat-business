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

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @author Hccake 2019/9/22 17:22
 */
@Data
@Schema(title = "系统用户查询对象")
@ParameterObject
public class SysUserQO {

	/**
	 * 登录账号
	 */
	@Parameter(description = "登录账号")
	private String username;

	/**
	 * 昵称
	 */
	@Parameter(description = "昵称")
	private String nickname;

	/**
	 * 性别(0-默认未知,1-男,2-女)
	 */
	@Parameter(description = "性别(0-默认未知,1-男,2-女)")
	private Integer gender;

	/**
	 * 电子邮件
	 */
	@Parameter(description = "电子邮件")
	private String email;

	/**
	 * 手机号
	 */
	@Parameter(description = "手机号")
	private String phoneNumber;

	/**
	 * 状态(1-正常,2-冻结)
	 */
	@Parameter(description = "状态(1-正常,2-冻结)")
	private Integer status;

	/**
	 * 组织机构ID
	 */
	@Parameter(description = "organizationId")
	private List<Long> organizationId;

	@Parameter(description = "用户类型:1:系统用户， 2：客户用户")
	private Integer type;

	@Parameter(description = "开始时间")
	private String startTime;

	@Parameter(description = "结束时间")
	private String endTime;

}
