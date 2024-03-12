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

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.system.mapper.SysRoleMapper;
import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.qo.SysRoleQO;
import org.ballcat.business.system.model.vo.SysRolePageVO;
import org.ballcat.business.system.service.SysRoleMenuService;
import org.ballcat.business.system.service.SysRoleService;
import org.ballcat.common.core.exception.BusinessException;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.domain.SelectData;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ballcat
 * @since 2017-10-29
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ExtendServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	private final SysRoleMenuService sysRoleMenuService;

	/**
	 * 查询系统角色列表
	 * @param pageParam 分页对象
	 * @param qo 查询参数
	 * @return 分页对象
	 */
	@Override
	public PageResult<SysRolePageVO> queryPage(PageParam pageParam, SysRoleQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

	/**
	 * 通过角色ID，删除角色,并清空角色菜单缓存
	 * @param id 角色ID
	 * @return boolean
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		SysRole role = getById(id);
		this.sysRoleMenuService.deleteByRoleCode(role.getCode());
		return SqlHelper.retBool(baseMapper.deleteById(id));
	}

	/**
	 * 角色的选择数据
	 * @return List<SelectData < ?>>
	 */
	@Override
	public List<SelectData<Void>> listSelectData() {
		return baseMapper.listSelectData();
	}

	/**
	 * 是否存在角色code
	 * @param roleCode 角色code
	 * @return boolean 是否存在
	 */
	@Override
	public boolean existsRoleCode(String roleCode) {
		return baseMapper.existsRoleCode(roleCode);
	}

	/**
	 * 新增角色
	 * @param sysRole 角色对象
	 * @return boolean 是否新增成功
	 */
	@Override
	public boolean save(SysRole sysRole) {
		if (existsRoleCode(sysRole.getCode())) {
			throw new BusinessException(BaseResultCode.LOGIC_CHECK_ERROR, "角色标识已存在！");
		}
		return SqlHelper.retBool(this.baseMapper.insert(sysRole));
	}

}
