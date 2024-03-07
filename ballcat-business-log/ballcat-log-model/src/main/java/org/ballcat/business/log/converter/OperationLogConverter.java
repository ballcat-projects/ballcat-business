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

package org.ballcat.business.log.converter;

import org.ballcat.business.log.model.entity.OperationLog;
import org.ballcat.business.log.model.vo.OperationLogPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 操作日志模型转换器
 *
 * @author hccake 2021-03-22 20:32:30
 */
@Mapper
public interface OperationLogConverter {

	OperationLogConverter INSTANCE = Mappers.getMapper(OperationLogConverter.class);

	/**
	 * PO 转 PageVO
	 * @param operationLog 操作日志
	 * @return AdminOperationLogPageVO 操作日志PageVO
	 */
	OperationLogPageVO poToPageVo(OperationLog operationLog);

}
