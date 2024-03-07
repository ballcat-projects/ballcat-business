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
import org.ballcat.business.system.model.qo.SysRoleQO;
import org.ballcat.business.system.model.vo.SysRolePageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.domain.SelectData;
import org.ballcat.mybatisplus.service.ExtendService;

/**
 * <p>
 * 系统角色服务类
 * </p>
 *
 * @author hccake
 * @since 2020-01-12
 */
public interface SysRoleService extends ExtendService<SysRole> {

	/**
	 * 查询系统角色列表
	 * @param pageParam 分页参数
	 * @param qo 查询参数
	 * @return 分页对象
	 */
	PageResult<SysRolePageVO> queryPage(PageParam pageParam, SysRoleQO qo);

	/**
	 * 角色的选择数据
	 * @return 角色下拉列表数据集合
	 */
	List<SelectData<Void>> listSelectData();

	/**
	 * 是否存在角色code
	 * @param roleCode 角色code
	 * @return boolean 是否存在
	 */
	boolean existsRoleCode(String roleCode);

}
