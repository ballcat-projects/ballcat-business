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

package org.ballcat.business.notify.model.qo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.business.notify.enums.NotifyRecipientFilterTypeEnum;
import org.springdoc.api.annotations.ParameterObject;

/**
 * 公告信息 查询对象
 *
 * @author hccake 2020-12-15 17:01:15
 */
@Data
@ParameterObject
@Schema(title = "公告信息查询对象")
public class AnnouncementQO {

	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	@Parameter(description = "标题")
	private String title;

	/**
	 * 接收人筛选方式
	 *
	 * @see NotifyRecipientFilterTypeEnum
	 */
	@Parameter(description = "接收人筛选方式")
	private Integer recipientFilterType;

	@Parameter(description = "状态")
	private Integer[] status;

	@Parameter(description = "接收方式")
	private Integer[] receiveMode;

}
