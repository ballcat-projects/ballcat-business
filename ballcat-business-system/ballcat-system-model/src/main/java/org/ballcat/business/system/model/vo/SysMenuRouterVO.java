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
 * 菜单权限视图对象
 *
 * @author hccake 2021-04-06 17:59:51
 */
@Data
@I18nClass
@Schema(title = "菜单权限视图对象")
public class SysMenuRouterVO {

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
	 * 菜单图标
	 */
	@Schema(title = "菜单图标")
	private String icon;

	/**
	 * 路由地址
	 */
	@Schema(title = "路由地址")
	private String path;

	/**
	 * 打开方式 (1组件 2内链 3外链)
	 */
	@Schema(title = "打开方式 (1组件 2内链 3外链)")
	private Integer targetType;

	/**
	 * 定位标识 (打开方式为组件时其值为组件相对路径，其他为URL地址)
	 */
	@Schema(title = "定位标识 (打开方式为组件时其值为组件相对路径，其他为URL地址)")
	private String uri;

	/**
	 * 组件缓存：0-开启，1-关闭
	 */
	@Schema(title = "组件缓存：0-开启，1-关闭")
	private Integer keepAlive;

	/**
	 * 隐藏菜单: 0-否，1-是
	 */
	@Schema(title = "隐藏菜单:  0-否，1-是")
	private Integer hidden;

	/**
	 * 菜单类型 （0目录，1菜单，2按钮）
	 */
	@Schema(title = "菜单类型 （0目录，1菜单，2按钮）")
	private Integer type;

	/**
	 * 备注信息
	 */
	@Schema(title = "备注信息")
	private String remarks;

}
