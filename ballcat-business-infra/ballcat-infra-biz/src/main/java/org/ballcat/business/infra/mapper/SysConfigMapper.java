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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.ballcat.business.infra.converter.SysConfigConverter;
import org.ballcat.business.infra.model.entity.SysConfig;
import org.ballcat.business.infra.model.qo.SysConfigQO;
import org.ballcat.business.infra.model.vo.SysConfigPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.ballcat.mybatisplus.toolkit.WrappersX;

/**
 * 系统配置表
 *
 * @author ballcat code generator 2019-10-14 17:42:23
 */
public interface SysConfigMapper extends ExtendMapper<SysConfig> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param sysConfigQO 查询参数
	 * @return PageResult<SysRoleVO>
	 */
	default PageResult<SysConfigPageVO> queryPage(PageParam pageParam, SysConfigQO sysConfigQO) {
		IPage<SysConfig> page = this.prodPage(pageParam);
		Wrapper<SysConfig> wrapper = WrappersX.lambdaQueryX(SysConfig.class)
			.likeIfPresent(SysConfig::getConfKey, sysConfigQO.getConfKey())
			.likeIfPresent(SysConfig::getName, sysConfigQO.getName())
			.likeIfPresent(SysConfig::getCategory, sysConfigQO.getCategory());
		this.selectPage(page, wrapper);
		IPage<SysConfigPageVO> voPage = page.convert(SysConfigConverter.INSTANCE::poToPageVo);
		return new PageResult<>(voPage.getRecords(), voPage.getTotal());
	}

	/**
	 * 根据配置key查询配置信息
	 * @param confKey 配置key
	 * @return SysConfig 配置信息
	 */
	default SysConfig selectByKey(String confKey) {
		return this.selectOne(Wrappers.<SysConfig>lambdaQuery().eq(SysConfig::getConfKey, confKey));
	}

	/**
	 * 根据 confKey 进行更新
	 * @param sysConfig 系统配置
	 * @return 更新是否成功
	 */
	default boolean updateByKey(SysConfig sysConfig) {
		Wrapper<SysConfig> wrapper = Wrappers.lambdaUpdate(SysConfig.class)
			.eq(SysConfig::getConfKey, sysConfig.getConfKey());
		int flag = this.update(sysConfig, wrapper);
		return SqlHelper.retBool(flag);
	}

	/**
	 * 根据 confKey 进行删除
	 * @param confKey 配置key
	 * @return 删除是否成功
	 */
	default boolean deleteByKey(String confKey) {
		Wrapper<SysConfig> wrapper = Wrappers.lambdaQuery(SysConfig.class).eq(SysConfig::getConfKey, confKey);
		int flag = this.delete(wrapper);
		return SqlHelper.retBool(flag);
	}

}
