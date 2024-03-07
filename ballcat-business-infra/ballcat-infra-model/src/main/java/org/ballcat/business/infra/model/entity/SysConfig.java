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
 * 系统配置表
 *
 * @author ballcat code generator 2019-10-14 17:42:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
@Schema(title = "基础配置")
public class SysConfig extends LogicDeletedBaseEntity {

	/**
	 * 主键
	 */
	@TableId
	@Schema(title = "主键ID")
	private Long id;

	/**
	 * 配置名称
	 */
	@Schema(title = "配置名称")
	private String name;

	/**
	 * 配置在缓存中的key名
	 */
	@Schema(title = "配置在缓存中的key名")
	private String confKey;

	/**
	 * 配置值
	 */
	@Schema(title = "配置值")
	private String confValue;

	/**
	 * 分类
	 */
	@Schema(title = "分类")
	private String category;

	/**
	 * 备注
	 */
	@Schema(title = "备注")
	private String remarks;

}
