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

package org.ballcat.admin.springsecurity;

import org.ballcat.business.system.component.AbstractPasswordHelper;
import org.ballcat.business.system.properties.SystemProperties;
import org.ballcat.security.properties.SecurityProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 基于 SpringSecurity 的密码工具类
 *
 * @author Hccake
 * @since 2.0.0
 */
public class SpringSecurityPasswordHelper extends AbstractPasswordHelper {

	private final PasswordEncoder passwordEncoder;

	public SpringSecurityPasswordHelper(SecurityProperties securityProperties, SystemProperties systemProperties,
			PasswordEncoder passwordEncoder) {
		super(securityProperties, systemProperties);
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String encode(String rawPassword) {
		return this.passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return this.passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
