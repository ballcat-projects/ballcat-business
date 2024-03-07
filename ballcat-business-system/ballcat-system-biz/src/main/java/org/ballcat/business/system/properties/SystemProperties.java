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

package org.ballcat.business.system.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 权限管理系统相关的基础配置
 *
 * @author Hccake 2021/4/23
 * @version 1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = SystemProperties.PREFIX)
public class SystemProperties {

	public static final String PREFIX = "ballcat.system";

	/**
	 * 超级管理员的配置
	 */
	private Administrator administrator = new Administrator();

	/**
	 * 密码的规则：值为正则表达式，当为空时，不对密码规则进行校验
	 */
	private String passwordRule;

	@Getter
	@Setter
	public static class Administrator {

		/**
		 * 指定id的用户为超级管理员
		 */
		private int userId = 0;

		/**
		 * 指定 username 为超级管理员
		 */
		private String username;

	}

}
