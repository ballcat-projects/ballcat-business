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

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ballcat.common.model.entity.LogicDeletedBaseEntity;

/**
 * 字典项
 *
 * @author hccake 2020-03-26 18:40:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dict_item", autoResultMap = true)
@Schema(title = "字典项")
public class SysDictItem extends LogicDeletedBaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	@Schema(title = "ID")
	private Long id;

	/**
	 * 字典标识
	 */
	@Schema(title = "字典标识")
	private String dictCode;

	/**
	 * 数据值
	 */
	@Schema(title = "数据值")
	private String value;

	/**
	 * 文本值
	 */
	@Schema(title = "文本值")
	private String name;

	/**
	 * 状态
	 */
	@Schema(title = "状态", description = "1：启用 0：禁用")
	private Integer status;

	/**
	 * 附加属性值
	 */
	@TableField(typeHandler = JacksonTypeHandler.class)
	@Schema(title = "附加属性值")
	private Map<String, Object> attributes;

	/**
	 * 排序（升序）
	 */
	@Schema(title = "排序（升序）")
	private Integer sort;

	/**
	 * 备注
	 */
	@Schema(title = "备注")
	private String remarks;

}
