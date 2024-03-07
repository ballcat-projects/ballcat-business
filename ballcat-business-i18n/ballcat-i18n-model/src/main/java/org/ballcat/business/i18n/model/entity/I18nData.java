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

package org.ballcat.business.i18n.model.entity;

import java.util.Objects;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ballcat.common.model.entity.BaseEntity;

/**
 * 国际化信息
 *
 * @author hccake 2021-08-06 10:48:25
 */
@Getter
@Setter
@ToString
@TableName("i18n_data")
@Schema(title = "国际化信息")
public class I18nData extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;

		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		I18nData i18nData = (I18nData) o;
		return this.languageTag.equals(i18nData.languageTag) && this.code.equals(i18nData.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.languageTag, this.code);
	}

}
