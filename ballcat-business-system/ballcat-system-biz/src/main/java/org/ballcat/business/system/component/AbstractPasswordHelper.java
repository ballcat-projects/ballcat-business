package org.ballcat.business.system.component;

import org.ballcat.business.system.properties.SystemProperties;
import org.ballcat.common.core.exception.BusinessException;
import org.ballcat.common.util.AesUtils;
import org.ballcat.security.properties.SecurityProperties;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			final byte[] secretKeyBytes = passwordSecretKey.getBytes();
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
		if (passwordPattern == null) {
			return true;
		}
		Matcher matcher = passwordPattern.matcher(rawPassword);
		return matcher.matches();
	}

}
