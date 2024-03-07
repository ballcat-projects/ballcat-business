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

package org.ballcat.business.i18n.converter;

import org.ballcat.business.i18n.model.dto.I18nDataDTO;
import org.ballcat.business.i18n.model.entity.I18nData;
import org.ballcat.business.i18n.model.vo.I18nDataExcelVO;
import org.ballcat.business.i18n.model.vo.I18nDataPageVO;
import org.ballcat.i18n.I18nMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 国际化信息模型转换器
 *
 * @author hccake 2021-08-06 10:48:25
 */
@Mapper
public interface I18nDataConverter {

	I18nDataConverter INSTANCE = Mappers.getMapper(I18nDataConverter.class);

	/**
	 * i18nMessage 转 I18nData
	 * @param i18nMessage 国际化消息
	 * @return I18nData 国际化信息实体
	 */
	I18nData messageToPo(I18nMessage i18nMessage);

	/**
	 * PO 转 PageVO
	 * @param i18nData 国际化信息
	 * @return I18nDataPageVO 国际化信息PageVO
	 */
	I18nDataPageVO poToPageVo(I18nData i18nData);

	/**
	 * PO 转 DTI
	 * @param i18nData 国际化信息
	 * @return I18nDataDTO 国际化信息DTO
	 */
	I18nDataDTO poToDto(I18nData i18nData);

	/**
	 * PO 转 ExcelVO
	 * @param i18nData 国际化信息
	 * @return I18nDataExcelVO 国际化信息ExcelVO
	 */
	I18nDataExcelVO poToExcelVo(I18nData i18nData);

	/**
	 * ExcelVO转 PO
	 * @param i18nDataExcelVO 国际化信息ExcelVO
	 * @return I18nData 国际化信息
	 */
	I18nData excelVoToPo(I18nDataExcelVO i18nDataExcelVO);

}
