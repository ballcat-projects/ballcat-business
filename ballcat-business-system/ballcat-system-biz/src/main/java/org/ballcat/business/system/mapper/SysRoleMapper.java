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

package org.ballcat.business.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.ballcat.business.system.converter.SysRoleConverter;
import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.qo.SysRoleQO;
import org.ballcat.business.system.model.vo.SysRolePageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.domain.SelectData;
import org.ballcat.mybatisplus.conditions.query.LambdaQueryWrapperX;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.ballcat.mybatisplus.toolkit.WrappersX;
import org.springframework.util.StringUtils;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ballcat
 * @since 2017-10-29
 */
public interface SysRoleMapper extends ExtendMapper<SysRole> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param qo 查询对象
	 * @return PageResult<SysRoleVO>
	 */
	default PageResult<SysRolePageVO> queryPage(PageParam pageParam, SysRoleQO qo) {
		IPage<SysRole> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<SysRole> wrapper = WrappersX.lambdaQueryX(SysRole.class)
			.likeIfPresent(SysRole::getName, qo.getName())
			.likeIfPresent(SysRole::getCode, qo.getCode())
			.between(StringUtils.hasText(qo.getStartTime()) && StringUtils.hasText(qo.getEndTime()),
					SysRole::getCreateTime, qo.getStartTime(), qo.getEndTime());
		this.selectPage(page, wrapper);
		IPage<SysRolePageVO> voPage = page.convert(SysRoleConverter.INSTANCE::poToPageVo);
		return new PageResult<>(voPage.getRecords(), voPage.getTotal());
	}

	/**
	 * 获取角色下拉框数据
	 * @return 下拉选择框数据集合
	 */
	List<SelectData<Void>> listSelectData();

	/**
	 * 是否存在角色code
	 * @param roleCode 角色code
	 * @return boolean 是否存在
	 */
	default boolean existsRoleCode(String roleCode) {
		LambdaQueryWrapperX<SysRole> wrapperX = new LambdaQueryWrapperX<>();
		wrapperX.eq(SysRole::getCode, roleCode);
		return this.selectCount(wrapperX) > 0L;
	}

}
