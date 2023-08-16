package org.ballcat.business.notify.recipient;

import org.ballcat.business.notify.enums.NotifyRecipientFilterTypeEnum;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.business.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class SpecifyUserRecipientFilter implements RecipientFilter {

	private final SysUserService sysUserService;

	/**
	 * 当前筛选器对应的筛选类型
	 * @return 筛选类型对应的标识
	 * @see NotifyRecipientFilterTypeEnum
	 */
	@Override
	public Integer filterType() {
		return NotifyRecipientFilterTypeEnum.SPECIFY_USER.getValue();
	}

	/**
	 * 接收者筛选
	 * @param filterCondition 筛选条件 用户ID集合
	 * @return 接收者集合
	 */
	@Override
	public List<SysUser> filter(List<String> filterCondition) {
		List<Long> userIds = filterCondition.stream().map(Long::valueOf).collect(Collectors.toList());
		return sysUserService.listByUserIds(userIds);
	}

	/**
	 * 获取当前用户的过滤属性
	 * @param sysUser 系统用户
	 * @return 该用户所对应筛选条件的属性
	 */
	@Override
	public Object getFilterAttr(SysUser sysUser) {
		return sysUser.getUserId();
	}

	/**
	 * 是否匹配当前用户
	 * @param filterAttr 筛选属性
	 * @param filterCondition 筛选条件
	 * @return boolean true: 是否匹配
	 */
	@Override
	public boolean match(Object filterAttr, List<String> filterCondition) {
		Long userId = (Long) filterAttr;
		return filterCondition.stream().map(Long::valueOf).anyMatch(x -> x.equals(userId));
	}

}
