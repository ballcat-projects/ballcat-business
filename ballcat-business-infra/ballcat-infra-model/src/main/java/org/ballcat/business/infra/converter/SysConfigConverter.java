package org.ballcat.business.infra.converter;

import org.ballcat.business.infra.model.entity.SysConfig;
import org.ballcat.business.infra.model.vo.SysConfigPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 基础配置
 *
 * @author hccake 2021-03-22 18:58:36
 */
@Mapper
public interface SysConfigConverter {

	SysConfigConverter INSTANCE = Mappers.getMapper(SysConfigConverter.class);

	/**
	 * PO 转 PageVO
	 * @param sysConfig 基础配置
	 * @return SysConfigPageVO 基础配置分页VO
	 */
	SysConfigPageVO poToPageVo(SysConfig sysConfig);

}
