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

import org.ballcat.business.system.model.dto.SysOrganizationDTO;
import org.ballcat.business.system.model.entity.SysOrganization;
import org.ballcat.business.system.model.qo.SysOrganizationQO;
import org.ballcat.business.system.model.vo.SysOrganizationTree;
import org.ballcat.mybatisplus.service.ExtendService;

/**
 * 组织架构
 *
 * @author hccake 2020-09-23 12:09:43
 */
public interface SysOrganizationService extends ExtendService<SysOrganization> {

	/**
	 * 返回组织架构的树形结构
	 * @param sysOrganizationQO 组织机构查询条件
	 * @return OrganizationTree
	 */
	List<SysOrganizationTree> listTree(SysOrganizationQO sysOrganizationQO);

	/**
	 * 创建一个新的组织机构
	 * @param sysOrganizationDTO 组织机构DTO
	 * @return boolean 创建成功/失败
	 */
	boolean create(SysOrganizationDTO sysOrganizationDTO);

	/**
	 * 更新一个已有的组织机构
	 * @param sysOrganizationDTO 组织机构DTO
	 * @return boolean 更新成功/失败
	 */
	boolean update(SysOrganizationDTO sysOrganizationDTO);

	/**
	 * 根据组织ID 查询除该组织下的所有儿子组织
	 * @param organizationId 组织机构ID
	 * @return List<SysOrganization> 该组织的儿子组织
	 */
	List<SysOrganization> listSubOrganization(Long organizationId);

	/**
	 * 根据组织ID 查询除该组织下的所有孩子（子孙）组织
	 * @param organizationId 组织机构ID
	 * @return List<SysOrganization> 该组织的孩子组织
	 */
	List<SysOrganization> listChildOrganization(Long organizationId);

	/**
	 * 校正组织机构层级和深度
	 * @return 校正是否成功
	 */
	boolean revisedHierarchyAndPath();

}
