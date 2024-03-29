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

package org.ballcat.business.notify.model.domain;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.ballcat.business.notify.enums.NotifyChannelEnum;
import org.ballcat.business.notify.enums.NotifyRecipientFilterTypeEnum;

/**
 * 公告通知信息
 *
 * @author Hccake 2020/12/23
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@Schema(title = "公告通知信息")
public class AnnouncementNotifyInfo implements NotifyInfo {

	/**
	 * ID
	 */
	@Schema(title = "公告ID")
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
	 * 接收人筛选方式
	 *
	 * @see NotifyRecipientFilterTypeEnum
	 */
	@Schema(title = "接收人筛选方式")
	private Integer recipientFilterType;

	/**
	 * 对应接收人筛选方式的条件信息
	 */
	@Schema(title = "对应接收人筛选方式的条件信息")
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

}
