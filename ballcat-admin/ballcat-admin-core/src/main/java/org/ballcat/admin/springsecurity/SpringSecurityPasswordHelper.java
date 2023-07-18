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
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
