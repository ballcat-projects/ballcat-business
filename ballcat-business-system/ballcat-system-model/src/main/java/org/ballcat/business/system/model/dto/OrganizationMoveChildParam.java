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

package org.ballcat.business.system.model.dto;

import lombok.Data;

/**
 * 组织机构移动子节点时的参数封装对象
 *
 * @author hccake
 */
@Data
public class OrganizationMoveChildParam {

	/**
	 * 父级id
	 */
	private Long parentId;

	/**
	 * 父级节点原始的层级信息
	 */
	private String originParentHierarchy;

	/**
	 * 父级节点原始的层级信息长度 + 1
	 */
	private int originParentHierarchyLengthPlusOne;

	/**
	 * 父级节点移动后的层级信息
	 */
	private String targetParentHierarchy;

	/**
	 * 移动前后的节点深度差
	 */
	private Integer depthDiff;

	/**
	 * 查询孙子节点的条件语句
	 */
	private String grandsonConditionalStatement;

}
