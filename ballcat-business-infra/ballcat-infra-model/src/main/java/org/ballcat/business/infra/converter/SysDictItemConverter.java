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

package org.ballcat.business.infra.converter;

import org.ballcat.business.infra.model.dto.SysDictItemDTO;
import org.ballcat.business.infra.model.entity.SysDictItem;
import org.ballcat.business.infra.model.vo.DictItemVO;
import org.ballcat.business.infra.model.vo.SysDictItemPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典项
 *
 * @author hccake 2021-03-22 19:55:41
 */
@Mapper
public interface SysDictItemConverter {

	SysDictItemConverter INSTANCE = Mappers.getMapper(SysDictItemConverter.class);

	/**
	 * PO 转 分页VO
	 * @param sysDictItem 字典项
	 * @return SysDictItemPageVO 字典项分页VO
	 */
	SysDictItemPageVO poToPageVo(SysDictItem sysDictItem);

	/**
	 * 字典项实体 转 VO
	 * @param sysDictItem 字典项
	 * @return 字典项VO
	 */
	DictItemVO poToItemVo(SysDictItem sysDictItem);

	/**
	 * 字典项传输对象转实体
	 * @param sysDictItemDTO 传输对象
	 * @return SysDictItem
	 */
	SysDictItem dtoToPo(SysDictItemDTO sysDictItemDTO);

}
