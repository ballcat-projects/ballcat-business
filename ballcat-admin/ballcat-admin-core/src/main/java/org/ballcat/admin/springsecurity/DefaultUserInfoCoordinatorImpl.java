package org.ballcat.admin.springsecurity;

import org.ballcat.admin.springsecurity.UserInfoCoordinator;
import org.ballcat.business.system.model.dto.UserInfoDTO;

import java.util.Map;

/**
 * 默认的用户信息协调者
 *
 * @author Hccake 2020/9/28
 * @version 1.0
 */
public class DefaultUserInfoCoordinatorImpl implements UserInfoCoordinator {

	@Override
	public Map<String, Object> coordinateAttribute(UserInfoDTO userInfoDTO, Map<String, Object> attribute) {
		return attribute;
	}

}
