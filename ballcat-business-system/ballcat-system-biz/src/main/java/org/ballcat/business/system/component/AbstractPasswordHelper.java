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

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ballcat.business.system.properties.SystemProperties;
import org.ballcat.common.core.exception.BusinessException;
import org.ballcat.common.util.AesUtils;
import org.ballcat.security.properties.SecurityProperties;
import org.springframework.util.StringUtils;

/**
 * 密码相关的操作的辅助类
 *
 * @author hccake
 */
public abstract class AbstractPasswordHelper implements PasswordHelper {

	private final String passwordSecretKey;

	private final Pattern passwordPattern;

	public AbstractPasswordHelper(SecurityProperties securityProperties, SystemProperties systemProperties) {
		this.passwordSecretKey = securityProperties.getPasswordSecretKey();
		String passwordRule = systemProperties.getPasswordRule();
		this.passwordPattern = StringUtils.hasText(passwordRule) ? Pattern.compile(passwordRule) : null;
	}

	/**
	 * 将前端传递过来的密文解密为明文
	 * @param aesPass AES加密后的密文
	 * @return 明文密码
	 */
	public String decodeAes(String aesPass) {
		try {
			final byte[] secretKeyBytes = this.passwordSecretKey.getBytes();
			final byte[] passBytes = java.util.Base64.getDecoder().decode(aesPass);
			final byte[] bytes = AesUtils.cbcDecrypt(passBytes, secretKeyBytes, secretKeyBytes);
			return new String(bytes, StandardCharsets.UTF_8);
		}
		catch (GeneralSecurityException ex) {
			throw new BusinessException(400, "密码密文解密异常！");
		}
	}

	/**
	 * 校验密码是否符合规则
	 * @param rawPassword 明文密码
	 * @return 符合返回 true
	 */
	public boolean validateRule(String rawPassword) {
		if (this.passwordPattern == null) {
			return true;
		}
		Matcher matcher = this.passwordPattern.matcher(rawPassword);
		return matcher.matches();
	}

}
