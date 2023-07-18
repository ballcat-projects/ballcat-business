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
