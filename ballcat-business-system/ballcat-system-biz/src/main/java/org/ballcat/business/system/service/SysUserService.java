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

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.ballcat.business.system.model.dto.SysUserDTO;
import org.ballcat.business.system.model.dto.SysUserScope;
import org.ballcat.business.system.model.dto.UserInfoDTO;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.business.system.model.qo.SysUserQO;
import org.ballcat.business.system.model.vo.SysUserPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.domain.SelectData;
import org.ballcat.mybatisplus.service.ExtendService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统用户表
 *
 * @author ballcat code generator 2019-09-12 20:39:31
 */
public interface SysUserService extends ExtendService<SysUser> {

	/**
	 * 根据QueryObject查询系统用户列表
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<SysUserVO> 分页数据
	 */
	PageResult<SysUserPageVO> queryPage(PageParam pageParam, SysUserQO qo);

	/**
	 * 根据用户名查询用户
	 * @param username 用户名
	 * @return SysUser
	 */
	SysUser getByUsername(String username);

	/**
	 * 获取用户详情信息
	 * @param user SysUser
	 * @return UserInfoDTO
	 */
	UserInfoDTO findUserInfo(SysUser user);

	/**
	 * 新增系统用户
	 * @param sysUserDto SysUserDTO
	 * @return boolean
	 */
	boolean addSysUser(SysUserDTO sysUserDto);

	/**
	 * 更新系统用户信息
	 * @param sysUserDTO 用户DTO
	 * @return boolean
	 */
	boolean updateSysUser(SysUserDTO sysUserDTO);

	/**
	 * 更新用户权限信息
	 * @param userId 用户ID
	 * @param sysUserScope 用户权限域
	 * @return boolean
	 */
	boolean updateUserScope(Long userId, SysUserScope sysUserScope);

	/**
	 * 根据userId删除 用户
	 * @param userId 用户ID
	 * @return boolean
	 */
	boolean deleteByUserId(Long userId);

	/**
	 * 修改用户密码
	 * @param userId 用户ID
	 * @param password 明文密码
	 * @return boolean
	 */
	boolean updatePassword(Long userId, String password);

	/**
	 * 批量修改用户状态
	 * @param userIds 用户ID集合
	 * @param status 状态
	 * @return boolean
	 */
	boolean updateUserStatusBatch(Collection<Long> userIds, Integer status);

	/**
	 * 修改系统用户头像
	 * @param file 头像文件
	 * @param userId 用户ID
	 * @return 文件相对路径
	 * @throws IOException IO异常
	 */
	String updateAvatar(MultipartFile file, Long userId) throws IOException;

	/**
	 * 根据角色查询用户
	 * @param roleCode 角色标识
	 * @return List<SysUser>
	 */
	List<SysUser> listByRoleCode(String roleCode);

	/**
	 * 根据角色查询用户
	 * @param roleCodes 角色标识集合
	 * @return List<SysUser> 用户集合
	 */
	List<SysUser> listByRoleCodes(Collection<String> roleCodes);

	/**
	 * 根据组织机构ID查询用户
	 * @param organizationIds 组织机构id集合
	 * @return 用户集合
	 */
	List<SysUser> listByOrganizationIds(Collection<Long> organizationIds);

	/**
	 * 根据用户类型查询用户
	 * @param userTypes 用户类型集合
	 * @return 用户集合
	 */
	List<SysUser> listByUserTypes(Collection<Integer> userTypes);

	/**
	 * 根据用户Id集合查询用户
	 * @param userIds 用户Id集合
	 * @return 用户集合
	 */
	List<SysUser> listByUserIds(Collection<Long> userIds);

	/**
	 * 返回用户的select数据
	 * @param type 为空时返回所有客户为1返回系统客户 name=> username value => userId
	 * @return List<SelectData>
	 */
	List<SelectData<Void>> listSelectData(Collection<Integer> type);

	/**
	 * 获取用户的角色Code集合
	 * @param userId 用户id
	 * @return List<String>
	 */
	List<String> listRoleCodes(Long userId);

	/**
	 * 是否存在指定组织的用户
	 * @param organizationId 组织 id
	 * @return boolean 存在返回 true
	 */
	boolean existsForOrganization(Long organizationId);

}
