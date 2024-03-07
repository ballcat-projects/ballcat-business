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

package org.ballcat.business.system.service;

import java.util.List;

import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.entity.SysUserRole;
import org.ballcat.business.system.model.qo.RoleBindUserQO;
import org.ballcat.business.system.model.vo.RoleBindUserVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.ExtendService;

/**
 * @author Hccake
 * <p>
 * 用户角色关联表
 */
public interface SysUserRoleService extends ExtendService<SysUserRole> {

	/**
	 * 删除用户的角色
	 * @param userId 用户ID
	 * @return 删除是否程
	 */
	boolean deleteByUserId(Long userId);

	/**
	 * 更新用户关联关系
	 * @param userId 用户ID
	 * @param roleCodes 角色标识集合
	 * @return boolean
	 */
	boolean updateUserRoles(Long userId, List<String> roleCodes);

	/**
	 * 添加用户角色关联关系
	 * @param userId 用户ID
	 * @param roleCodes 角色标识集合
	 * @return 插入是否成功
	 */
	boolean addUserRoles(Long userId, List<String> roleCodes);

	/**
	 * 通过用户ID，查询角色列表
	 * @param userId 用户ID
	 * @return List<SysRole>
	 */
	List<SysRole> listRoles(Long userId);

	/**
	 * 通过角色标识，查询用户列表
	 * @param pageParam 分页参数
	 * @param roleCode 角色标识
	 * @return PageResult<RoleBindUserVO> 角色授权的用户列表
	 */
	PageResult<RoleBindUserVO> queryUserPageByRoleCode(PageParam pageParam, RoleBindUserQO roleCode);

	/**
	 * 解绑角色和用户关系
	 * @param userId 用户ID
	 * @param roleCode 角色标识
	 * @return 解绑成功：true
	 */
	boolean unbindRoleUser(Long userId, String roleCode);

	/**
	 * 通过用户ID，查询角色Code列表
	 * @param userId 用户ID
	 * @return List<String>
	 */
	List<String> listRoleCodes(Long userId);

}
