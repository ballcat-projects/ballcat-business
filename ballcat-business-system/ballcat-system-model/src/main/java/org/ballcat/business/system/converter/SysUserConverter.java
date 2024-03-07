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

package org.ballcat.business.system.converter;

import org.ballcat.business.system.model.dto.SysUserDTO;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.business.system.model.vo.SysUserInfo;
import org.ballcat.business.system.model.vo.SysUserPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Hccake
 * @version 1.0
 * @date 2019/9/17 15:26
 */
@Mapper
public interface SysUserConverter {

	SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

	/**
	 * 转换DTO 为 PO
	 * @param sysUserDTO 系统用户DTO
	 * @return SysUser 系统用户
	 */
	@Mapping(target = "password", ignore = true)
	SysUser dtoToPo(SysUserDTO sysUserDTO);

	/**
	 * PO 转 PageVO
	 * @param sysUser 系统用户
	 * @return SysUserPageVO 系统用户PageVO
	 */
	SysUserPageVO poToPageVo(SysUser sysUser);

	/**
	 * PO 转 Info
	 * @param sysUser 系统用户
	 * @return SysUserInfo 用户信息
	 */
	SysUserInfo poToInfo(SysUser sysUser);

}
