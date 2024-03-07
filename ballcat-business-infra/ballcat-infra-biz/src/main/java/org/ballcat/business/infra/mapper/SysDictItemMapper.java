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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.ballcat.business.infra.converter.SysDictItemConverter;
import org.ballcat.business.infra.model.entity.SysDictItem;
import org.ballcat.business.infra.model.vo.SysDictItemPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.mapper.ExtendMapper;

/**
 * 字典项
 *
 * @author hccake 2020-03-26 18:40:20
 */
public interface SysDictItemMapper extends ExtendMapper<SysDictItem> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param dictCode 字典标识
	 * @return PageResult<SysRoleVO>
	 */
	default PageResult<SysDictItemPageVO> queryPage(PageParam pageParam, String dictCode) {
		IPage<SysDictItem> page = this.prodPage(pageParam);
		LambdaQueryWrapper<SysDictItem> wrapper = Wrappers.lambdaQuery(SysDictItem.class)
			.eq(SysDictItem::getDictCode, dictCode);
		this.selectPage(page, wrapper);
		IPage<SysDictItemPageVO> voPage = page.convert(SysDictItemConverter.INSTANCE::poToPageVo);
		return new PageResult<>(voPage.getRecords(), voPage.getTotal());
	}

	/**
	 * 根据字典标识查询对应字典项集合
	 * @param dictCode 字典标识
	 * @return List<SysDictItem> 字典项集合
	 */
	default List<SysDictItem> listByDictCode(String dictCode) {
		return this.selectList(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictCode, dictCode));
	}

	/**
	 * 根据字典标识删除对应字典项
	 * @param dictCode 字典标识
	 * @return 是否删除成功
	 */
	default boolean deleteByDictCode(String dictCode) {
		int i = this.delete(Wrappers.<SysDictItem>lambdaUpdate().eq(SysDictItem::getDictCode, dictCode));
		return SqlHelper.retBool(i);
	}

	/**
	 * 判断是否存在指定字典标识的字典项
	 * @param dictCode 字典标识
	 * @return boolean 存在：true
	 */
	default boolean existsDictItem(String dictCode) {
		return this.exists(Wrappers.lambdaQuery(SysDictItem.class).eq(SysDictItem::getDictCode, dictCode));
	}

}
