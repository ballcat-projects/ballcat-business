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

package org.ballcat.business.system.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hccake 2019/9/17 14:54
 */
public final class SysUserConst {

	private SysUserConst() {
	}

	@Getter
	@AllArgsConstructor
	public enum Status {

		/**
		 * 正常
		 */
		NORMAL(1),
		/**
		 * 锁定的
		 */
		LOCKED(0);

		private final Integer value;

	}

	/**
	 * 用户类型，1系统用户，2平台用户
	 */
	@Getter
	@AllArgsConstructor
	public enum Type {

		/**
		 * 系统用户
		 */
		SYSTEM(1),
		/**
		 * 平台用户
		 */
		CUSTOMER(2);

		private final Integer value;

	}

}
