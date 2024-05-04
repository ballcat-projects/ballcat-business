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

package org.ballcat.business.infra.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.ballcat.business.infra.converter.SysDictItemConverter;
import org.ballcat.business.infra.event.DictChangeEvent;
import org.ballcat.business.infra.model.dto.SysDictItemDTO;
import org.ballcat.business.infra.model.entity.SysDict;
import org.ballcat.business.infra.model.entity.SysDictItem;
import org.ballcat.business.infra.model.qo.SysDictQO;
import org.ballcat.business.infra.model.vo.DictDataVO;
import org.ballcat.business.infra.model.vo.DictItemVO;
import org.ballcat.business.infra.model.vo.SysDictItemPageVO;
import org.ballcat.business.infra.model.vo.SysDictPageVO;
import org.ballcat.business.infra.service.SysDictItemService;
import org.ballcat.business.infra.service.SysDictService;
import org.ballcat.common.core.exception.BusinessException;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.common.util.Assert;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author Hccake 2020/3/27 19:50
 */
@Service
@RequiredArgsConstructor
public class SysDictManager {

	private final SysDictService sysDictService;

	private final SysDictItemService sysDictItemService;

	private final ApplicationEventPublisher eventPublisher;

	/**
	 * 字典表分页
	 * @param pageParam 分页参数
	 * @param sysDictQO 查询参数
	 * @return 字典表分页数据
	 */
	public PageResult<SysDictPageVO> dictPage(PageParam pageParam, SysDictQO sysDictQO) {
		return this.sysDictService.queryPage(pageParam, sysDictQO);
	}

	/**
	 * 保存字典
	 * @param sysDict 字典对象
	 * @return 执行是否成功
	 */
	public boolean dictSave(SysDict sysDict) {
		sysDict.setHashCode(ObjectId.get().toString());
		return this.sysDictService.save(sysDict);
	}

	/**
	 * 更新字典
	 * @param sysDict 字典对象
	 * @return 执行是否成功
	 */
	public boolean updateDictById(SysDict sysDict) {
		// 查询现有数据
		SysDict dict = this.sysDictService.getById(sysDict.getId());
		sysDict.setHashCode(ObjectId.get().toString());
		boolean result = this.sysDictService.updateById(sysDict);
		if (result) {
			this.eventPublisher.publishEvent(new DictChangeEvent(dict.getCode()));
		}
		return result;
	}

	/**
	 * 删除字典
	 * @param id 字典id
	 */
	@Transactional(rollbackFor = Exception.class)
	public void removeDictById(Long id) {
		// 查询现有数据
		SysDict dict = this.sysDictService.getById(id);
		// 字典标识
		String dictCode = dict.getCode();

		// 有关联字典项则不允许删除
		Assert.isTrue(!this.sysDictItemService.exist(dictCode),
				() -> new BusinessException(BaseResultCode.LOGIC_CHECK_ERROR.getCode(), "该字典下存在字典项，不允许删除！"));

		// 删除字典
		Assert.isTrue(this.sysDictService.removeById(id),
				() -> new BusinessException(BaseResultCode.UPDATE_DATABASE_ERROR.getCode(), "字典删除异常"));
	}

	/**
	 * 更新字典项状态
	 * @param itemId 字典项id
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateDictItemStatusById(Long itemId, Integer status) {
		// 获取字典项
		SysDictItem dictItem = this.sysDictItemService.getById(itemId);
		Assert.notNull(dictItem,
				() -> new BusinessException(BaseResultCode.LOGIC_CHECK_ERROR.getCode(), "错误的字典项 id：" + itemId));

		// 更新字典项状态
		SysDictItem entity = new SysDictItem();
		entity.setId(itemId);
		entity.setStatus(status);
		Assert.isTrue(this.sysDictItemService.updateById(entity),
				() -> new BusinessException(BaseResultCode.UPDATE_DATABASE_ERROR.getCode(), "字典项状态更新异常"));

		// 更新字典 hash
		String dictCode = dictItem.getDictCode();
		Assert.isTrue(this.sysDictService.updateHashCode(dictCode),
				() -> new BusinessException(BaseResultCode.UPDATE_DATABASE_ERROR.getCode(), "字典 Hash 更新异常"));

		// 发布字典更新事件
		this.eventPublisher.publishEvent(new DictChangeEvent(dictCode));
	}

	/**
	 * 通过id刷新hash值
	 * @param id 字典id
	 */
	public void refreshDictHashById(Long id) {
		// 查询现有数据
		SysDict dict = this.sysDictService.getById(id);
		Assert.notNull(dict, () -> new BusinessException(BaseResultCode.LOGIC_CHECK_ERROR.getCode(), "错误的字典 id：" + id));

		// 更新字典 hash
		String dictCode = dict.getCode();
		Assert.isTrue(this.sysDictService.updateHashCode(dictCode),
				() -> new BusinessException(BaseResultCode.UPDATE_DATABASE_ERROR.getCode(), "字典 Hash 更新异常"));

		// 发布字典更新事件
		this.eventPublisher.publishEvent(new DictChangeEvent(dictCode));
	}

	/**
	 * 字典项分页
	 * @param pageParam 分页属性
	 * @param dictCode 字典标识
	 * @return 字典项分页数据
	 */
	public PageResult<SysDictItemPageVO> dictItemPage(PageParam pageParam, String dictCode) {
		return this.sysDictItemService.queryPage(pageParam, dictCode);
	}

	/**
	 * 新增字典项
	 * @param sysDictItemDTO 字典项
	 * @return 执行是否成功
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean saveDictItem(SysDictItemDTO sysDictItemDTO) {
		// 更新字典项Hash值
		String dictCode = sysDictItemDTO.getDictCode();
		if (!this.sysDictService.updateHashCode(dictCode)) {
			return false;
		}

		SysDictItem sysDictItem = SysDictItemConverter.INSTANCE.dtoToPo(sysDictItemDTO);
		boolean result = this.sysDictItemService.save(sysDictItem);
		if (result) {
			this.eventPublisher.publishEvent(new DictChangeEvent(dictCode));
		}
		return result;
	}

	/**
	 * 更新字典项
	 * @param sysDictItemDTO 字典项
	 * @return 执行是否成功
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateDictItemById(SysDictItemDTO sysDictItemDTO) {
		// 根据ID查询字典
		String dictCode = sysDictItemDTO.getDictCode();
		// 更新字典项Hash值
		if (!this.sysDictService.updateHashCode(dictCode)) {
			return false;
		}

		SysDictItem sysDictItem = SysDictItemConverter.INSTANCE.dtoToPo(sysDictItemDTO);
		boolean result = this.sysDictItemService.updateById(sysDictItem);
		if (result) {
			this.eventPublisher.publishEvent(new DictChangeEvent(dictCode));
		}
		return result;
	}

	/**
	 * 删除字典项
	 * @param id 字典项
	 * @return 执行是否成功
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean removeDictItemById(Long id) {
		// 根据ID查询字典
		SysDictItem dictItem = this.sysDictItemService.getById(id);
		String dictCode = dictItem.getDictCode();
		// 更新字典项Hash值
		if (!this.sysDictService.updateHashCode(dictCode)) {
			return false;
		}
		boolean result = this.sysDictItemService.removeById(id);
		if (result) {
			this.eventPublisher.publishEvent(new DictChangeEvent(dictCode));
		}
		return true;
	}

	/**
	 * 查询字典数据
	 * @param dictCodes 字典标识
	 * @return DictDataAndHashVO
	 */
	public List<DictDataVO> queryDictDataAndHashVO(String[] dictCodes) {
		// 查询对应hash值，以及字典项数据
		List<SysDict> sysDictList = this.sysDictService.listByCodes(dictCodes);
		if (CollectionUtils.isEmpty(sysDictList)) {
			return new ArrayList<>();
		}

		// 填充字典项
		List<DictDataVO> list = new ArrayList<>();
		for (SysDict sysDict : sysDictList) {
			List<SysDictItem> dictItems = this.sysDictItemService.listByDictCode(sysDict.getCode());
			// 排序并转换为VO
			List<DictItemVO> setDictItems = dictItems.stream()
				.sorted(Comparator.comparingInt(SysDictItem::getSort))
				.map(SysDictItemConverter.INSTANCE::poToItemVo)
				.collect(Collectors.toList());
			// 组装DataVO
			DictDataVO dictDataVO = new DictDataVO();
			dictDataVO.setValueType(sysDict.getValueType());
			dictDataVO.setDictCode(sysDict.getCode());
			dictDataVO.setHashCode(sysDict.getHashCode());
			dictDataVO.setDictItems(setDictItems);

			list.add(dictDataVO);
		}
		return list;
	}

	/**
	 * 返回失效的Hash
	 * @param dictHashCode 校验的hashCodeMap
	 * @return List<String> 失效的字典标识集合
	 */
	public List<String> invalidDictHash(Map<String, String> dictHashCode) {
		// @formatter:off
		List<SysDict> byCode = this.sysDictService.listByCodes(dictHashCode.keySet()
				.toArray(new String[] {}));
		// 过滤相等Hash值的字典项，并返回需要修改的字典项的Code
		return byCode.stream()
				.filter(x -> !dictHashCode.get(x.getCode()).equals(x.getHashCode()))
				.map(SysDict::getCode)
				.collect(Collectors.toList());
		// @formatter:on
	}

}
