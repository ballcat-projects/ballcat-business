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

package org.ballcat.business.system.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.infra.service.FileService;
import org.ballcat.business.system.checker.AdminUserChecker;
import org.ballcat.business.system.component.PasswordHelper;
import org.ballcat.business.system.constant.SysUserConst;
import org.ballcat.business.system.converter.SysUserConverter;
import org.ballcat.business.system.event.UserCreatedEvent;
import org.ballcat.business.system.event.UserOrganizationChangeEvent;
import org.ballcat.business.system.mapper.SysUserMapper;
import org.ballcat.business.system.model.dto.SysUserDTO;
import org.ballcat.business.system.model.dto.SysUserScope;
import org.ballcat.business.system.model.dto.UserInfoDTO;
import org.ballcat.business.system.model.entity.SysMenu;
import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.business.system.model.qo.SysUserQO;
import org.ballcat.business.system.model.vo.SysUserPageVO;
import org.ballcat.business.system.service.SysMenuService;
import org.ballcat.business.system.service.SysRoleService;
import org.ballcat.business.system.service.SysUserRoleService;
import org.ballcat.business.system.service.SysUserService;
import org.ballcat.common.constant.Symbol;
import org.ballcat.common.core.exception.BusinessException;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.domain.SelectData;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.common.util.Assert;
import org.ballcat.common.util.FileUtils;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统用户表
 *
 * @author ballcat code generator 2019-09-12 20:39:31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ExtendServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private final FileService fileService;

	private final SysMenuService sysMenuService;

	private final SysUserRoleService sysUserRoleService;

	private final AdminUserChecker adminUserChecker;

	private final SysRoleService sysRoleService;

	private final ApplicationEventPublisher publisher;

	private final PasswordHelper passwordHelper;

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<SysUserVO> 分页数据
	 */
	@Override
	public PageResult<SysUserPageVO> queryPage(PageParam pageParam, SysUserQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

	/**
	 * 根据用户名查询用户
	 * @param username 用户名
	 * @return 系统用户
	 */
	@Override
	public SysUser getByUsername(String username) {
		return baseMapper.selectByUsername(username);
	}

	/**
	 * 通过查用户的全部信息
	 * @param sysUser 用户
	 * @return 用户信息
	 */
	@Override
	public UserInfoDTO findUserInfo(SysUser sysUser) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setSysUser(sysUser);

		// 超级管理员拥有所有角色
		List<SysRole> roleList;
		if (this.adminUserChecker.isAdminUser(sysUser)) {
			roleList = this.sysRoleService.list();
		}
		else {
			roleList = this.sysUserRoleService.listRoles(sysUser.getUserId());
		}

		// 设置角色标识
		Set<String> roleCodes = new HashSet<>();
		for (SysRole role : roleList) {
			roleCodes.add(role.getCode());
		}
		userInfoDTO.setRoles(new HashSet<>(roleList));
		userInfoDTO.setRoleCodes(roleCodes);

		// 设置权限列表（permission）
		Set<String> permissions = new HashSet<>();
		Set<SysMenu> menus = new HashSet<>();
		for (String roleCode : roleCodes) {
			List<SysMenu> sysMenuList = this.sysMenuService.listByRoleCode(roleCode);
			menus.addAll(sysMenuList);
			List<String> permissionList = sysMenuList.stream()
				.map(SysMenu::getPermission)
				.filter(StringUtils::hasText)
				.collect(Collectors.toList());
			permissions.addAll(permissionList);
		}
		userInfoDTO.setMenus(menus);
		userInfoDTO.setPermissions(permissions);

		return userInfoDTO;
	}

	/**
	 * 新增系统用户
	 * @param sysUserDto 系统用户DTO
	 * @return 添加成功：true , 失败：false
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addSysUser(SysUserDTO sysUserDto) {
		SysUser sysUser = SysUserConverter.INSTANCE.dtoToPo(sysUserDto);
		sysUser.setType(SysUserConst.Type.SYSTEM.getValue());
		// 对密码进行加密
		String rawPassword = sysUserDto.getPassword();
		String encodedPassword = this.passwordHelper.encode(rawPassword);
		sysUser.setPassword(encodedPassword);

		// 保存用户
		boolean insertSuccess = SqlHelper.retBool(baseMapper.insert(sysUser));
		Assert.isTrue(insertSuccess, () -> {
			log.error("[addSysUser] 数据插入系统用户表失败，user：{}", sysUserDto);
			return new BusinessException(BaseResultCode.UPDATE_DATABASE_ERROR.getCode(), "数据插入系统用户表失败");
		});

		// 新增用户角色关联
		List<String> roleCodes = sysUserDto.getRoleCodes();
		if (!CollectionUtils.isEmpty(roleCodes)) {
			boolean addUserRoleSuccess = this.sysUserRoleService.addUserRoles(sysUser.getUserId(), roleCodes);
			Assert.isTrue(addUserRoleSuccess, () -> {
				log.error("[addSysUser] 更新用户角色信息失败，user：{}， roleCodes: {}", sysUserDto, roleCodes);
				return new BusinessException(BaseResultCode.UPDATE_DATABASE_ERROR.getCode(), "更新用户角色信息失败");
			});
		}

		// 发布用户创建事件
		this.publisher.publishEvent(new UserCreatedEvent(sysUser, sysUserDto.getRoleCodes()));

		return true;
	}

	/**
	 * 更新系统用户信息
	 * @param sysUserDTO 系统用户DTO
	 * @return 更新成功 true: 更新失败 false
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateSysUser(SysUserDTO sysUserDTO) {
		SysUser entity = SysUserConverter.INSTANCE.dtoToPo(sysUserDTO);
		org.springframework.util.Assert.isTrue(this.adminUserChecker.hasModifyPermission(entity), "当前用户不允许修改!");

		// 如果不更新组织，直接执行
		Long currentOrganizationId = entity.getOrganizationId();
		if (currentOrganizationId == null) {
			return SqlHelper.retBool(baseMapper.updateById(entity));
		}

		// 查询出当前库中用户
		Long userId = entity.getUserId();
		SysUser oldUser = baseMapper.selectById(userId);
		org.springframework.util.Assert.notNull(oldUser, "修改用户失败，当前用户不存在：" + userId);

		// 是否修改了组织
		Long originOrganizationId = oldUser.getOrganizationId();
		boolean organizationIdModified = !currentOrganizationId.equals(originOrganizationId);
		// 是否更改成功
		boolean isUpdateSuccess = SqlHelper.retBool(baseMapper.updateById(entity));
		// 如果修改了组织且修改成功，则发送用户组织更新事件
		if (isUpdateSuccess && organizationIdModified) {
			this.publisher
				.publishEvent(new UserOrganizationChangeEvent(userId, originOrganizationId, currentOrganizationId));
		}

		return isUpdateSuccess;
	}

	/**
	 * 更新用户权限信息
	 * @param userId 用户Id
	 * @param sysUserScope 系统用户权限范围
	 * @return 更新成功：true
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateUserScope(Long userId, SysUserScope sysUserScope) {
		// 更新用户角色关联关系
		return this.sysUserRoleService.updateUserRoles(userId, sysUserScope.getRoleCodes());
	}

	/**
	 * 根据userId删除 用户
	 * @param userId 用户ID
	 * @return 删除成功：true
	 */
	@Override
	public boolean deleteByUserId(Long userId) {
		org.springframework.util.Assert.isTrue(!this.adminUserChecker.isAdminUser(getById(userId)), "管理员不允许删除!");
		return SqlHelper.retBool(baseMapper.deleteById(userId));
	}

	/**
	 * 修改用户密码
	 * @param userId 用户ID
	 * @param rawPassword 明文密码
	 * @return 更新成功：true
	 */
	@Override
	public boolean updatePassword(Long userId, String rawPassword) {
		org.springframework.util.Assert.isTrue(this.adminUserChecker.hasModifyPermission(getById(userId)),
				"当前用户不允许修改!");
		// 密码加密加密
		String encodedPassword = this.passwordHelper.encode(rawPassword);
		return baseMapper.updatePassword(userId, encodedPassword);
	}

	/**
	 * 批量修改用户状态
	 * @param userIds 用户ID集合
	 * @return 更新成功：true
	 */
	@Override
	public boolean updateUserStatusBatch(Collection<Long> userIds, Integer status) {

		List<SysUser> userList = baseMapper.listByUserIds(userIds);
		org.springframework.util.Assert.notEmpty(userList, "更新用户状态失败，待更新用户列表为空");

		// 移除无权限更改的用户id
		Map<Long, SysUser> userMap = userList.stream()
			.collect(Collectors.toMap(SysUser::getUserId, Function.identity()));
		userIds.removeIf(id -> !this.adminUserChecker.hasModifyPermission(userMap.get(id)));
		org.springframework.util.Assert.notEmpty(userIds, "更新用户状态失败，无权限更新用户");

		return baseMapper.updateUserStatusBatch(userIds, status);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String updateAvatar(MultipartFile file, Long userId) throws IOException {
		org.springframework.util.Assert.isTrue(this.adminUserChecker.hasModifyPermission(getById(userId)),
				"当前用户不允许修改!");
		// 获取系统用户头像的文件名
		String objectName = "sysuser/" + userId + "/avatar/" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
				+ Symbol.SLASH + ObjectId.get() + Symbol.DOT + FileUtils.getExtension(file.getOriginalFilename());
		objectName = this.fileService.upload(file.getInputStream(), objectName, file.getSize());

		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setAvatar(objectName);
		baseMapper.updateById(sysUser);

		return objectName;
	}

	/**
	 * 根据角色查询用户
	 * @param roleCode 角色标识
	 * @return 系统用户集合
	 */
	@Override
	public List<SysUser> listByRoleCode(String roleCode) {
		return listByRoleCodes(Collections.singletonList(roleCode));
	}

	/**
	 * 根据角色查询用户
	 * @param roleCodes 角色标识集合
	 * @return List<SysUser>
	 */
	@Override
	public List<SysUser> listByRoleCodes(Collection<String> roleCodes) {
		return baseMapper.listByRoleCodes(roleCodes);
	}

	/**
	 * 根据组织机构ID查询用户
	 * @param organizationIds 组织机构id集合
	 * @return 用户集合
	 */
	@Override
	public List<SysUser> listByOrganizationIds(Collection<Long> organizationIds) {
		return baseMapper.listByOrganizationIds(organizationIds);
	}

	/**
	 * 根据用户类型查询用户
	 * @param userTypes 用户类型集合
	 * @return 用户集合
	 */
	@Override
	public List<SysUser> listByUserTypes(Collection<Integer> userTypes) {
		return baseMapper.listByUserTypes(userTypes);
	}

	/**
	 * 根据用户Id集合查询用户
	 * @param userIds 用户Id集合
	 * @return 用户集合
	 */
	@Override
	public List<SysUser> listByUserIds(Collection<Long> userIds) {
		return baseMapper.listByUserIds(userIds);

	}

	/**
	 * 返回用户的select数据 name=> username value => userId
	 * @param userTypes 用户类型
	 * @return List<SelectData>
	 */
	@Override
	public List<SelectData<Void>> listSelectData(Collection<Integer> userTypes) {
		return baseMapper.listSelectData(userTypes);
	}

	/**
	 * 获取用户的角色Code集合
	 * @param userId 用户id
	 * @return List<String>
	 */
	@Override
	public List<String> listRoleCodes(Long userId) {
		return this.sysUserRoleService.listRoles(userId).stream().map(SysRole::getCode).collect(Collectors.toList());
	}

	/**
	 * 是否存在指定组织的用户
	 * @param organizationId 组织 id
	 * @return boolean 存在返回 true
	 */
	@Override
	public boolean existsForOrganization(Long organizationId) {
		return baseMapper.existsForOrganization(organizationId);
	}

}
