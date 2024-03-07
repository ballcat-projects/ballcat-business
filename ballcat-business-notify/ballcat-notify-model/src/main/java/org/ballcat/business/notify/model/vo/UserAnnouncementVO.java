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

package org.ballcat.business.notify.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户公告表
 *
 * @author evil0th 2024-1-30 16:04:53
 */
@Data
@Schema(title = "用户公告分页VO")
public class UserAnnouncementVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 公告ID
	 */
	@Schema(title = "ID")
	private Long id;

	/**
	 * 标题
	 */
	@Schema(title = "标题")
	private String title;

	/**
	 * 内容
	 */
	@Schema(title = "内容")
	private String content;

	/**
	 * 状态，已读(1)|未读(0)
	 */
	@Schema(title = "状态，已读(1)|未读(0)")
	private Integer state;

	/**
	 * 创建人ID
	 */
	@Schema(title = "创建人ID")
	private Integer createBy;

	/**
	 * 创建人名称
	 */
	@Schema(title = "创建人名称")
	private String createUsername;

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

}
