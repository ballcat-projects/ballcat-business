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
