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
import lombok.EqualsAndHashCode;
import org.ballcat.common.model.entity.LogicDeletedBaseEntity;
import org.ballcat.mybatisplus.alias.TableAlias;

/**
 * 系统用户表
 *
 * @author ballcat code generator 2019-09-12 20:39:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableAlias("su")
@TableName("sys_user")
@Schema(title = "系统用户表")
public class SysUser extends LogicDeletedBaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	@Schema(title = "用户ID")
	private Long userId;

	/**
	 * 登录账号
	 */
	@Schema(title = "登录账号")
	private String username;

	/**
	 * 昵称
	 */
	@Schema(title = "昵称")
	private String nickname;

	/**
	 * 密码
	 */
	@Schema(title = "密码")
	private String password;

	/**
	 * md5密码盐
	 */
	@Schema(title = "md5密码盐")
	private String salt;

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
	 * 状态(1-正常,0-冻结)
	 */
	@Schema(title = "状态(1-正常, 0-冻结)")
	private Integer status;

	/**
	 * 组织机构ID
	 */
	@Schema(title = "组织机构ID")
	private Long organizationId;

	/**
	 * 用户类型
	 */
	@Schema(title = "1:系统用户， 2：客户用户")
	private Integer type;

}
