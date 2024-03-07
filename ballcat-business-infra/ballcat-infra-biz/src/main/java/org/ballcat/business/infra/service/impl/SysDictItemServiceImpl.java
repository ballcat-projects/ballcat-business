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

package org.ballcat.business.infra.service.impl;

import java.util.List;

import org.ballcat.business.infra.mapper.SysDictItemMapper;
import org.ballcat.business.infra.model.entity.SysDictItem;
import org.ballcat.business.infra.model.vo.SysDictItemPageVO;
import org.ballcat.business.infra.service.SysDictItemService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 字典项
 *
 * @author hccake 2020-03-26 18:40:20
 */
@Service
public class SysDictItemServiceImpl extends ExtendServiceImpl<SysDictItemMapper, SysDictItem>
		implements SysDictItemService {

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param dictCode 字典标识
	 * @return 分页数据
	 */
	@Override
	public PageResult<SysDictItemPageVO> queryPage(PageParam pageParam, String dictCode) {
		return baseMapper.queryPage(pageParam, dictCode);
	}

	/**
	 * 根据Code查询对应字典项数据
	 * @param dictCode 字典标识
	 * @return 字典项集合
	 */
	@Override
	public List<SysDictItem> listByDictCode(String dictCode) {
		return baseMapper.listByDictCode(dictCode);
	}

	/**
	 * 根据字典标识删除对应字典项
	 * @param dictCode 字典标识
	 * @return 是否删除成功
	 */
	@Override
	public boolean removeByDictCode(String dictCode) {
		// 如果存在字典项则进行删除
		if (baseMapper.existsDictItem(dictCode)) {
			return baseMapper.deleteByDictCode(dictCode);
		}
		return true;
	}

	/**
	 * 判断字典项是否存在
	 * @param dictCode 字典标识
	 * @return 存在返回 true
	 */
	@Override
	public boolean exist(String dictCode) {
		return baseMapper.existsDictItem(dictCode);
	}

}
