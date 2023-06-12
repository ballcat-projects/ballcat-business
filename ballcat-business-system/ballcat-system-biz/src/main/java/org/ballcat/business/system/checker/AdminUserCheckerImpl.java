package org.ballcat.business.system.checker;

import lombok.RequiredArgsConstructor;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.business.system.properties.SystemProperties;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 超级管理员账户规则配置
 *
 * @author lingting 2020-06-24 21:00:15
 */
@Service
@RequiredArgsConstructor
public class AdminUserCheckerImpl implements AdminUserChecker {

	private final SystemProperties systemProperties;

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	@Override
	public boolean isAdminUser(SysUser user) {
		SystemProperties.Administrator administrator = systemProperties.getAdministrator();
		if (administrator.getUserId() == user.getUserId()) {
			return true;
		}
		return StringUtils.hasText(administrator.getUsername())
				&& administrator.getUsername().equals(user.getUsername());
	}

	@Override
	public boolean hasModifyPermission(SysUser targetUser) {
		// 如果需要修改的用户是超级管理员，则只能本人修改
		if (this.isAdminUser(targetUser)) {
			return principalAttributeAccessor.getName().equals(targetUser.getUsername());
		}
		return true;
	}

}
