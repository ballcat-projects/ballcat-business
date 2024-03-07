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

package org.ballcat.business.system.model.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.common.util.tree.TreeNode;

/**
 * 组织架构
 *
 * @author hccake 2020-09-23 12:09:43
 */
@Data
@Schema(title = "组织架构")
public class SysOrganizationTree implements TreeNode<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Schema(title = "ID")
	private Long id;

	/**
	 * 组织名称
	 */
	@Schema(title = "组织名称")
	private String name;

	/**
	 * 父级ID
	 */
	@Schema(title = "父级ID")
	private Long parentId;

	/**
	 * 层级信息，从根节点到当前节点的最短路径，使用-分割节点ID
	 */
	@Schema(title = "层级信息，从根节点到当前节点的最短路径，使用-分割节点ID")
	private String hierarchy;

	/**
	 * 当前节点深度
	 */
	@Schema(title = "当前节点深度")
	private Integer depth;

	/**
	 * 排序字段，由小到大
	 */
	@Schema(title = "排序字段，由小到大")
	private Integer sort;

	/**
	 * 描述信息
	 */
	@Schema(title = "描述信息")
	private String remarks;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(title = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 下级组织
	 */
	@Schema(title = "下级组织")
	List<SysOrganizationTree> children = new ArrayList<>();

	/**
	 * 设置节点的子节点列表
	 * @param children 子节点
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends TreeNode<Long>> void setChildren(List<T> children) {
		this.children = (List<SysOrganizationTree>) children;
	}

	@Override
	public Long getKey() {
		return this.id;
	}

	@Override
	public Long getParentKey() {
		return this.parentId;
	}

}
