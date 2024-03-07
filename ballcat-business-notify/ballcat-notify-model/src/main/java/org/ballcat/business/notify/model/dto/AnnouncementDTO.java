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

package org.ballcat.business.notify.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.business.notify.enums.NotifyChannelEnum;

/**
 * 公告信息
 *
 * @author hccake 2020-12-15 17:01:15
 */
@Data
@Schema(title = "公告信息")
public class AnnouncementDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Schema(title = "ID")
	private Long id;

	/**
	 * 标题
	 */
	@NotBlank(message = "标题不能为空")
	@Schema(title = "标题")
	private String title;

	/**
	 * 内容
	 */
	@NotBlank(message = "内容不能为空")
	@Schema(title = "内容")
	private String content;

	/**
	 * 接收人筛选方式，1：全部 2：用户角色 3：组织机构 4：用户类型 5：自定义用户
	 */
	@NotNull(message = "接收人范围不能为空")
	@Schema(title = "接收人范围")
	private Integer recipientFilterType;

	/**
	 * 对应接收人筛选方式的条件信息，多个用逗号分割。如角色标识，组织ID，用户类型，用户ID等
	 */
	@Schema(title = "对应接收人筛选方式的条件信息。如角色标识，组织ID，用户类型，用户ID等")
	private List<String> recipientFilterCondition;

	/**
	 * 接收方式，值与通知渠道一一对应
	 *
	 * @see NotifyChannelEnum
	 */
	@Schema(title = "接收方式")
	private List<Integer> receiveMode;

	/**
	 * 永久有效的
	 *
	 * @see org.ballcat.common.core.constant.enums.BooleanEnum
	 */
	@Schema(title = "永久有效的")
	private Integer immortal;

	/**
	 * 截止日期
	 */
	@Schema(title = "截止日期")
	private LocalDateTime deadline;

	/**
	 * 状态
	 */
	@Schema(title = "状态")
	private Integer status;

}
