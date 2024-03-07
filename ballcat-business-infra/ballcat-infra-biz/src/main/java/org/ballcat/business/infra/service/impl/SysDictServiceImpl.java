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

import java.util.ArrayList;
import java.util.List;

import org.ballcat.business.infra.mapper.SysDictMapper;
import org.ballcat.business.infra.model.entity.SysDict;
import org.ballcat.business.infra.model.qo.SysDictQO;
import org.ballcat.business.infra.model.vo.SysDictPageVO;
import org.ballcat.business.infra.service.SysDictService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * 字典表
 *
 * @author hccake 2020-03-26 18:40:20
 */
@Service
public class SysDictServiceImpl extends ExtendServiceImpl<SysDictMapper, SysDict> implements SysDictService {

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<SysDictVO> 分页数据
	 */
	@Override
	public PageResult<SysDictPageVO> queryPage(PageParam pageParam, SysDictQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

	/**
	 * 根据字典标识查询
	 * @param dictCode 字典标识
	 * @return 字典数据
	 */
	@Override
	public SysDict getByCode(String dictCode) {
		return baseMapper.getByCode(dictCode);
	}

	/**
	 * 根据字典标识数组查询对应字典集合
	 * @param dictCodes 字典标识数组
	 * @return List<SysDict> 字典集合
	 */
	@Override
	public List<SysDict> listByCodes(String[] dictCodes) {
		if (dictCodes == null || dictCodes.length == 0) {
			return new ArrayList<>();
		}
		return baseMapper.listByCodes(dictCodes);
	}

	/**
	 * 更新字典HashCode
	 * @param dictCode 字典标识
	 * @return 更新状态: 成功(true) 失败(false)
	 */
	@Override
	public boolean updateHashCode(String dictCode) {
		return baseMapper.updateHashCode(dictCode, ObjectId.get().toString());
	}

}
