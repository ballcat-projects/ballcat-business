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

package org.ballcat.admin.upms.config.mybatis;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.ballcat.mybatisplus.injector.CustomSqlInjector;
import org.ballcat.mybatisplus.methods.InsertBatchSomeColumnByCollection;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认配置MybatisPlus分页插件，通过conditional注解达到覆盖效用
 *
 * @author hccake 2020/04/19
 */
@Configuration
public class MybatisPlusConfig {

	/**
	 * MybatisPlusInterceptor 插件，默认提供分页插件</br>
	 * 如需其他MP内置插件，则需自定义该Bean
	 * @return MybatisPlusInterceptor
	 */
	@Bean
	@ConditionalOnMissingBean(MybatisPlusInterceptor.class)
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}

	/**
	 * 自动填充处理类
	 * @return FillMetaObjectHandle
	 */
	@Bean
	@ConditionalOnMissingBean(MetaObjectHandler.class)
	public MetaObjectHandler fillMetaObjectHandle(PrincipalAttributeAccessor principalAttributeAccessor) {
		return new FillMetaObjectHandle(principalAttributeAccessor);
	}

	/**
	 * 自定义批量插入方法注入
	 * @return ISqlInjector
	 */
	@Bean
	@ConditionalOnMissingBean(ISqlInjector.class)
	public ISqlInjector customSqlInjector() {
		List<AbstractMethod> list = new ArrayList<>();
		// 对于只在更新时进行填充的字段不做插入处理
		list.add(new InsertBatchSomeColumnByCollection(t -> t.getFieldFill() != FieldFill.UPDATE));
		return new CustomSqlInjector(list);
	}

}
