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

package org.ballcat.business.infra.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.ballcat.business.infra.converter.SysDictConverter;
import org.ballcat.business.infra.model.entity.SysDict;
import org.ballcat.business.infra.model.qo.SysDictQO;
import org.ballcat.business.infra.model.vo.SysDictPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.conditions.query.LambdaQueryWrapperX;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.ballcat.mybatisplus.toolkit.WrappersX;

/**
 * 字典表
 *
 * @author hccake 2020-03-26 18:40:20
 */
public interface SysDictMapper extends ExtendMapper<SysDict> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param qo 查询对象
	 * @return PageResult<SysRoleVO>
	 */
	default PageResult<SysDictPageVO> queryPage(PageParam pageParam, SysDictQO qo) {
		IPage<SysDict> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<SysDict> wrapper = WrappersX.lambdaQueryX(SysDict.class)
			.likeIfPresent(SysDict::getCode, qo.getCode())
			.likeIfPresent(SysDict::getTitle, qo.getTitle());
		this.selectPage(page, wrapper);
		IPage<SysDictPageVO> voPage = page.convert(SysDictConverter.INSTANCE::poToPageVo);
		return new PageResult<>(voPage.getRecords(), voPage.getTotal());
	}

	/**
	 * 根据字典标识查询对应字典
	 * @param dictCode 字典标识
	 * @return SysDict 字典
	 */
	default SysDict getByCode(String dictCode) {
		return this.selectOne(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getCode, dictCode));
	}

	/**
	 * 根据字典标识数组查询对应字典集合
	 * @param dictCodes 字典标识数组
	 * @return List<SysDict> 字典集合
	 */
	default List<SysDict> listByCodes(String[] dictCodes) {
		return this.selectList(Wrappers.<SysDict>lambdaQuery().in(SysDict::getCode, (Object[]) dictCodes));
	}

	/**
	 * 更新字典的HashCode
	 * @param dictCode 字典标识
	 * @param hashCode 哈希值
	 * @return boolean 是否更新成功
	 */
	default boolean updateHashCode(String dictCode, String hashCode) {
		int flag = this.update(null,
				Wrappers.<SysDict>lambdaUpdate().set(SysDict::getHashCode, hashCode).eq(SysDict::getCode, dictCode));
		return SqlHelper.retBool(flag);
	}

}
