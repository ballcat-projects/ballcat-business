package org.ballcat.business.system.converter;

import org.ballcat.business.system.model.dto.SysRoleUpdateDTO;
import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.vo.SysRolePageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统角色POJO转换器
 *
 * @author Hccake 2020/7/6
 * @version 1.0
 */
@Mapper
public interface SysRoleConverter {

	SysRoleConverter INSTANCE = Mappers.getMapper(SysRoleConverter.class);

	/**
	 * PO 转 PageVO
	 * @param sysRole 系统角色
	 * @return SysRolePageVO 系统角色分页VO
	 */
	SysRolePageVO poToPageVo(SysRole sysRole);

	/**
	 * 修改DTO 转 PO
	 * @param dto 修改DTO
	 * @return SysRole PO
	 */
	SysRole dtoToPo(SysRoleUpdateDTO dto);

}
