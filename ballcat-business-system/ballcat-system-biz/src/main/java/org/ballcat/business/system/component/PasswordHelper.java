package org.ballcat.business.system.component;

import org.ballcat.business.system.properties.SystemProperties;
import org.ballcat.common.core.exception.BusinessException;
import org.ballcat.security.properties.SecurityProperties;
import org.ballcat.springsecurity.util.PasswordUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码相关的操作的辅助类
 *
 * @author hccake
 */
@Component
public class PasswordHelper {

	private final SecurityProperties securityProperties;

	private final PasswordEncoder passwordEncoder;

	private final Pattern passwordPattern;

	public PasswordHelper(SecurityProperties securityProperties, SystemProperties systemProperties,
			PasswordEncoder passwordEncoder) {
		this.securityProperties = securityProperties;
		this.passwordEncoder = passwordEncoder;
		String passwordRule = systemProperties.getPasswordRule();
		this.passwordPattern = StringUtils.hasText(passwordRule) ? Pattern.compile(passwordRule) : null;
	}

	/**
	 * 密码加密，单向加密，不可逆
	 * @param rawPassword 明文密码
	 * @return 加密后的密文
	 */
	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	/**
	 * 将前端传递过来的密文解密为明文
	 * @param aesPass AES加密后的密文
	 * @return 明文密码
	 */
	public String decodeAes(String aesPass) {
		try {
			return PasswordUtils.decodeAES(aesPass, securityProperties.getPasswordSecretKey());
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
		if (passwordPattern == null) {
			return true;
		}
		Matcher matcher = passwordPattern.matcher(rawPassword);
		return matcher.matches();
	}

}
