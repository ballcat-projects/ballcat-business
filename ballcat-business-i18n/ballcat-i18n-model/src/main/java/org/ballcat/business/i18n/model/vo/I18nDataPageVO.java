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

package org.ballcat.business.i18n.model.vo;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 国际化信息分页视图对象
 *
 * @author hccake 2021-08-06 10:48:25
 */
@Data
@Schema(title = "国际化信息分页视图对象")
public class I18nDataPageVO {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Schema(title = "ID")
	private Long id;

	/**
	 * 语言标签
	 */
	@Schema(title = "语言标签")
	private String languageTag;

	/**
	 * 国际化标识
	 */
	@Schema(title = "国际化标识")
	private String code;

	/**
	 * 文本值，可以使用 { } 加角标，作为占位符
	 */
	@Schema(title = "文本值，可以使用 { } 加角标，作为占位符")
	private String message;

	/**
	 * 备注
	 */
	@Schema(title = "备注")
	private String remarks;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(title = "修改时间")
	private LocalDateTime updateTime;

}
