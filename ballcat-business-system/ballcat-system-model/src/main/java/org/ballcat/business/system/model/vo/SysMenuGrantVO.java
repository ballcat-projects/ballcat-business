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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.i18n.I18nClass;
import org.ballcat.i18n.I18nField;

/**
 * 菜单权限授权对象
 *
 * @author hccake 2021-04-06 17:59:51
 */
@Data
@I18nClass
@Schema(title = "菜单权限授权对象")
public class SysMenuGrantVO {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@Schema(title = "菜单ID")
	private Long id;

	/**
	 * 父级ID
	 */
	@Schema(title = "父级ID")
	private Long parentId;

	/**
	 * 菜单名称
	 */
	@I18nField(condition = "type != 2")
	@Schema(title = "菜单名称")
	private String title;

	/**
	 * 菜单类型 （0目录，1菜单，2按钮）
	 */
	@Schema(title = "菜单类型 （0目录，1菜单，2按钮）")
	private Integer type;

}
