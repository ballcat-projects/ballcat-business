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

package org.ballcat.business.system.converter;

import org.ballcat.business.system.model.dto.SysOrganizationDTO;
import org.ballcat.business.system.model.entity.SysOrganization;
import org.ballcat.business.system.model.vo.SysOrganizationTree;
import org.ballcat.business.system.model.vo.SysOrganizationVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Hccake 2020/9/23
 * @version 1.0
 */
@Mapper
public interface SysOrganizationConverter {

	SysOrganizationConverter INSTANCE = Mappers.getMapper(SysOrganizationConverter.class);

	/**
	 * 实体转树节点实体
	 * @param sysOrganization 组织架构实体
	 * @return 组织架构树类型
	 */
	SysOrganizationTree poToTree(SysOrganization sysOrganization);

	/**
	 * 实体转组织架构VO
	 * @param sysOrganization 组织架构实体
	 * @return SysOrganizationVO
	 */
	SysOrganizationVO poToVo(SysOrganization sysOrganization);

	/**
	 * 新增传输对象转实体
	 * @param sysOrganizationDTO 组织机构DTO
	 * @return 组织机构实体
	 */
	SysOrganization dtoToPo(SysOrganizationDTO sysOrganizationDTO);

}
