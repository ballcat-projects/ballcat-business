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

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户公告表
 *
 * @author hccake 2020-12-25 08:04:53
 */
@Data
@Schema(title = "用户公告分页VO")
public class UserAnnouncementPageVO {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Schema(title = "ID")
	private Long id;

	/**
	 * 公告id
	 */
	@Schema(title = "公告id")
	private Long announcementId;

	/**
	 * 用户ID
	 */
	@Schema(title = "用户ID")
	private Long userId;

	/**
	 * 状态，已读(1)|未读(0)
	 */
	@Schema(title = "状态，已读(1)|未读(0)")
	private Integer state;

	/**
	 * 阅读时间
	 */
	@Schema(title = "阅读时间")
	private LocalDateTime readTime;

	/**
	 * 拉取时间
	 */
	@Schema(title = "拉取时间")
	private LocalDateTime createTime;

}
