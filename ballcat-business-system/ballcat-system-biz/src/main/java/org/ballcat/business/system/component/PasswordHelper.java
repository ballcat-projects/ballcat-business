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

package org.ballcat.business.system.component;

/**
 * 密码相关的操作的辅助类
 *
 * @author hccake
 */
public interface PasswordHelper {

	/**
	 * 密码加密，单向加密，不可逆
	 * @param rawPassword 明文密码
	 * @return 加密后的密文
	 */
	String encode(String rawPassword);

	/**
	 * 将前端传递过来的密文解密为明文
	 * @param aesPass AES加密后的密文
	 * @return 明文密码
	 */
	String decodeAes(String aesPass);

	/**
	 * 校验密码是否符合规则
	 * @param rawPassword 明文密码
	 * @return 符合返回 true
	 */
	boolean validateRule(String rawPassword);

	/**
	 * 校验密码
	 * @param rawPassword 明文密码
	 * @param encodedPassword 加密密码(数据库密码)
	 * @return 符合返回 true
	 */
	boolean matches(String rawPassword, String encodedPassword);

}
