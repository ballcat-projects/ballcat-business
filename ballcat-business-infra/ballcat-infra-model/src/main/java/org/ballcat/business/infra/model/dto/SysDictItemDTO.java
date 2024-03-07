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

package org.ballcat.business.infra.model.dto;

import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.common.core.validation.constraints.OneOfInts;
import org.ballcat.common.core.validation.group.CreateGroup;
import org.ballcat.common.core.validation.group.UpdateGroup;

/**
 * 字典项
 *
 * @author hccake 2020-03-26 18:40:20
 */
@Data
@Schema(title = "字典项")
public class SysDictItemDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Null(message = "id {}", groups = CreateGroup.class)
	@NotNull(message = "id {}", groups = UpdateGroup.class)
	@Schema(title = "ID")
	private Long id;

	/**
	 * 字典标识
	 */
	@NotBlank(message = "dictCode {}")
	@Schema(title = "字典标识")
	private String dictCode;

	/**
	 * 数据值
	 */
	@NotBlank(message = "value {}")
	@Schema(title = "数据值")
	private String value;

	/**
	 * 文本值
	 */
	@NotBlank(message = "name {}")
	@Schema(title = "文本值")
	private String name;

	/**
	 * 状态
	 */
	@NotNull(message = "status {}", groups = CreateGroup.class)
	@OneOfInts(value = { 1, 0 }, message = "status {}", allowNull = true)
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
	@NotNull
	@Min(value = 0, message = "sort {}")
	@Schema(title = "排序（升序）")
	private Integer sort;

	/**
	 * 备注
	 */
	@Schema(title = "备注")
	private String remarks;

}
