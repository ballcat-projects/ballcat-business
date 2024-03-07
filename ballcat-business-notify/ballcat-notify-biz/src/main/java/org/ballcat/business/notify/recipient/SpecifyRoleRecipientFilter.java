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

package org.ballcat.business.notify.recipient;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.ballcat.business.notify.enums.NotifyRecipientFilterTypeEnum;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.business.system.service.SysUserService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class SpecifyRoleRecipientFilter implements RecipientFilter {

	private final SysUserService sysUserService;

	/**
	 * 当前筛选器对应的筛选类型
	 * @return 筛选类型对应的标识
	 * @see NotifyRecipientFilterTypeEnum
	 */
	@Override
	public Integer filterType() {
		return NotifyRecipientFilterTypeEnum.SPECIFY_ROLE.getValue();
	}

	/**
	 * 接收者筛选
	 * @param filterCondition 筛选条件
	 * @return 接收者集合
	 */
	@Override
	public List<SysUser> filter(List<String> filterCondition) {
		return this.sysUserService.listByRoleCodes(filterCondition);
	}

	/**
	 * 获取当前用户的过滤属性
	 * @param sysUser 系统用户
	 * @return 该用户所对应筛选条件的属性
	 */
	@Override
	public Object getFilterAttr(SysUser sysUser) {
		return this.sysUserService.listRoleCodes(sysUser.getUserId());
	}

	/**
	 * 是否匹配当前用户
	 * @param filterAttr 筛选属性
	 * @param filterCondition 筛选条件
	 * @return boolean true: 是否匹配
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean match(Object filterAttr, List<String> filterCondition) {
		if (!(filterAttr instanceof List)) {
			return false;
		}
		List<String> roleCodes = (List<String>) filterAttr;
		if (CollectionUtils.isEmpty(roleCodes)) {
			return false;
		}
		for (Object roleCode : roleCodes) {
			boolean matched = filterCondition.stream().anyMatch(x -> x.equals(roleCode));
			if (matched) {
				return true;
			}
		}
		return false;
	}

}
