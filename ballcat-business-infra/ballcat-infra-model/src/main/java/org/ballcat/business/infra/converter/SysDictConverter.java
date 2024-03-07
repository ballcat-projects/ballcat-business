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

import org.ballcat.business.infra.model.entity.SysDict;
import org.ballcat.business.infra.model.vo.SysDictPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Hccake 2020/4/9 16:32
 */
@Mapper
public interface SysDictConverter {

	SysDictConverter INSTANCE = Mappers.getMapper(SysDictConverter.class);

	/**
	 * 字典实体转VO
	 * @param sysDict 字典
	 * @return SysDictPageVO 字典分页VO
	 */
	SysDictPageVO poToPageVo(SysDict sysDict);

}
