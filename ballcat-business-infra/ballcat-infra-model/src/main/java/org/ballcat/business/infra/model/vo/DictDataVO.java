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

package org.ballcat.business.infra.model.vo;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Hccake 2020/4/9 15:48
 */
@Data
@Schema(title = "字典数据VO")
public class DictDataVO {

	/**
	 * 字典标识
	 */
	@Schema(title = "字典标识")
	private String dictCode;

	/**
	 * 字典值类型
	 */
	@Schema(title = "字典值类型")
	private Integer valueType;

	/**
	 * 字典Hash值
	 */
	@Schema(title = "字典Hash值")
	private String hashCode;

	/**
	 * 字典项列表
	 */
	@Schema(title = "字典项列表")
	private List<DictItemVO> dictItems;

}
