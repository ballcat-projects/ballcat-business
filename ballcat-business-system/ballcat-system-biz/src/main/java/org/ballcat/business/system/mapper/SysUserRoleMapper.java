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

package org.ballcat.business.system.mapper;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.annotations.Param;
import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.entity.SysUserRole;
import org.ballcat.business.system.model.qo.RoleBindUserQO;
import org.ballcat.business.system.model.vo.RoleBindUserVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.ballcat.mybatisplus.toolkit.WrappersX;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author hccake
 * @since 2017-10-29
 */
public interface SysUserRoleMapper extends ExtendMapper<SysUserRole> {

	/**
	 * 删除用户关联关系
	 * @param userId 用户ID
	 * @return boolean 删除是否成功
	 */
	default boolean deleteByUserId(Long userId) {
		int i = this.delete(Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId));
		return SqlHelper.retBool(i);
	}

	/**
	 * 插入用户角色关联关系
	 * @param list 用户角色关联集合
	 * @return boolean 插入是否成功
	 */
	default boolean insertUserRoles(List<SysUserRole> list) {
		int i = this.insertBatchSomeColumn(list);
		return SqlHelper.retBool(i);
	}

	/**
	 * 用户是否存在角色绑定关系
	 * @param userId 用户ID
	 * @param roleCode 角色标识，可为空
	 * @return 存在：true
	 */
	default boolean existsRoleBind(Long userId, String roleCode) {
		Long num = this.selectCount(WrappersX.lambdaQueryX(SysUserRole.class)
			.eq(SysUserRole::getUserId, userId)
			.eqIfPresent(SysUserRole::getRoleCode, roleCode));
		return SqlHelper.retBool(num);
	}

	/**
	 * 通过角色标识，查询用户列表
	 * @param pageParam 分页参数
	 * @param roleBindUserQO 角色标识
	 * @return List<SysUser> 角色授权的用户列表
	 */
	default PageResult<RoleBindUserVO> queryUserPageByRoleCode(PageParam pageParam, RoleBindUserQO roleBindUserQO) {
		// TODO 连表查询排序，这里暂时禁用
		pageParam.setSorts(new ArrayList<>());
		IPage<RoleBindUserVO> page = this.prodPage(pageParam);
		this.queryUserPageByRoleCode(page, roleBindUserQO);
		return new PageResult<>(page.getRecords(), page.getTotal());
	}

	/**
	 * 删除角色和用户关系
	 * @param userId 用户ID
	 * @param roleCode 角色标识
	 * @return 删除成功：true
	 */
	default boolean deleteUserRole(Long userId, String roleCode) {
		int i = this.delete(Wrappers.lambdaQuery(SysUserRole.class)
			.eq(SysUserRole::getUserId, userId)
			.eq(SysUserRole::getRoleCode, roleCode));
		return SqlHelper.retBool(i);
	}

	/**
	 * 通过用户ID，查询角色
	 * @param userId 用户ID
	 * @return 用户拥有的角色集合
	 */
	List<SysRole> listRoleByUserId(Long userId);

	/**
	 * 通过角色标识，查询用户列表
	 * @param roleCode 角色标识
	 * @return List<SysUser> 角色授权的用户列表
	 */
	IPage<RoleBindUserVO> queryUserPageByRoleCode(IPage<RoleBindUserVO> page, @Param("qo") RoleBindUserQO roleCode);

	/**
	 * 通过用户ID，查询角色codes
	 * @param userId 用户ID
	 * @return 用户拥有的角色code集合
	 */
	List<String> selectRoleCodeByUserId(Long userId);

}
