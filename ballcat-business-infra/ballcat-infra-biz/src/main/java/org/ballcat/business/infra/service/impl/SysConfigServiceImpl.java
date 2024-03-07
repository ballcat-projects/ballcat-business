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

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.ballcat.business.infra.constant.SystemRedisKeyConstants;
import org.ballcat.business.infra.mapper.SysConfigMapper;
import org.ballcat.business.infra.model.entity.SysConfig;
import org.ballcat.business.infra.model.qo.SysConfigQO;
import org.ballcat.business.infra.model.vo.SysConfigPageVO;
import org.ballcat.business.infra.service.SysConfigService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.ballcat.redis.core.annotation.CacheDel;
import org.ballcat.redis.core.annotation.Cached;
import org.springframework.stereotype.Service;

/**
 * 系统配置表
 *
 * @author ballcat code generator 2019-10-14 17:42:23
 */
@Service
public class SysConfigServiceImpl extends ExtendServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

	@Override
	public PageResult<SysConfigPageVO> queryPage(PageParam pageParam, SysConfigQO sysConfigQO) {
		return baseMapper.queryPage(pageParam, sysConfigQO);
	}

	@Cached(key = SystemRedisKeyConstants.SYSTEM_CONFIG_PREFIX, keyJoint = "#confKey")
	@Override
	public String getConfValueByKey(String confKey) {
		SysConfig sysConfig = baseMapper.selectByKey(confKey);
		return sysConfig == null ? null : sysConfig.getConfValue();
	}

	/**
	 * 保存系统配置，由于查询不到时会缓存空值，所以新建时也需要删除对应 key，防止之前误存了空值数据
	 * @param entity 实体对象
	 * @return 保存成功 true
	 */
	@CacheDel(key = SystemRedisKeyConstants.SYSTEM_CONFIG_PREFIX, keyJoint = "#p0.confKey")
	@Override
	public boolean save(SysConfig entity) {
		return SqlHelper.retBool(baseMapper.insert(entity));
	}

	@CacheDel(key = SystemRedisKeyConstants.SYSTEM_CONFIG_PREFIX, keyJoint = "#sysConfig.confKey")
	@Override
	public boolean updateByKey(SysConfig sysConfig) {
		return baseMapper.updateByKey(sysConfig);
	}

	@CacheDel(key = SystemRedisKeyConstants.SYSTEM_CONFIG_PREFIX, keyJoint = "#confKey")
	@Override
	public boolean removeByKey(String confKey) {
		return baseMapper.deleteByKey(confKey);
	}

}
