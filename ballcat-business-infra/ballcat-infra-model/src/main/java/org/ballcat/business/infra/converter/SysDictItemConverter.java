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
