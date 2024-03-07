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

package org.ballcat.business.infra.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ballcat.common.model.entity.LogicDeletedBaseEntity;

/**
 * 字典表
 *
 * @author hccake 2020-03-26 18:40:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
@Schema(title = "字典表")
public class SysDict extends LogicDeletedBaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	@Schema(title = "编号")
	private Long id;

	/**
	 * 标识
	 */
	@Schema(title = "标识")
	private String code;

	/**
	 * 名称
	 */
	@Schema(title = "名称")
	private String title;

	/**
	 * Hash值
	 */
	@Schema(title = "Hash值")
	private String hashCode;

	/**
	 * 数据类型
	 */
	@Schema(title = "数据类型", description = "1:Number 2:String 3:Boolean")
	private Integer valueType;

	/**
	 * 备注
	 */
	@Schema(title = "备注")
	private String remarks;

}
