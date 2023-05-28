package org.ballcat.business.system.converter;

import org.ballcat.business.system.model.entity.SysDict;
import org.ballcat.business.system.model.vo.SysDictPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Hccake
 * @version 1.0
 * @date 2020/4/9 16:32
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
