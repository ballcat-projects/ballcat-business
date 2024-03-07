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

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Param;
import org.ballcat.business.system.model.dto.OrganizationMoveChildParam;
import org.ballcat.business.system.model.entity.SysOrganization;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.springframework.lang.Nullable;

/**
 * 组织架构
 *
 * @author hccake 2020-09-23 12:09:43
 */
public interface SysOrganizationMapper extends ExtendMapper<SysOrganization> {

	/**
	 * 根据组织ID 查询除该组织下的所有儿子组织
	 * @param organizationId 组织机构ID
	 * @return List<SysOrganization> 该组织的儿子组织
	 */
	default List<SysOrganization> listSubOrganization(Long organizationId) {
		LambdaQueryWrapper<SysOrganization> wrapper = Wrappers.<SysOrganization>lambdaQuery()
			.eq(SysOrganization::getParentId, organizationId);
		return this.selectList(wrapper);
	}

	/**
	 * 跟随父节点移动子节点
	 * @param param OrganizationMoveChildParam 跟随移动子节点的参数对象
	 */
	void followMoveChildNode(@Param("param") OrganizationMoveChildParam param);

	/**
	 * 根据组织机构Id，查询该组织下的所有子部门
	 * @param organizationId 组织机构ID
	 * @return 子部门集合
	 */
	List<SysOrganization> listChildOrganization(@Param("organizationId") Long organizationId);

	/**
	 * 批量更新节点层级和深度
	 * @param depth 深度
	 * @param hierarchy 层级
	 * @param organizationIds 组织id集合
	 */
	default void updateHierarchyAndPathBatch(int depth, String hierarchy, List<Long> organizationIds) {
		LambdaUpdateWrapper<SysOrganization> wrapper = Wrappers.lambdaUpdate(SysOrganization.class)
			.set(SysOrganization::getDepth, depth)
			.set(SysOrganization::getHierarchy, hierarchy)
			.in(SysOrganization::getId, organizationIds);
		this.update(null, wrapper);

	}

	/**
	 * 检查指定机构下是否存在子机构
	 * @param organizationId 机构id
	 * @return 存在返回 true
	 */
	@Nullable
	Boolean existsChildOrganization(Long organizationId);

}
