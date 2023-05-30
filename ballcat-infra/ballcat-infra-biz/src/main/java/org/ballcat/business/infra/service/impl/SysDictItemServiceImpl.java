package org.ballcat.business.infra.service.impl;

import org.ballcat.business.infra.mapper.SysDictItemMapper;
import org.ballcat.business.infra.model.entity.SysDictItem;
import org.ballcat.business.infra.model.vo.SysDictItemPageVO;
import org.ballcat.business.infra.service.SysDictItemService;
import com.hccake.ballcat.common.model.domain.PageParam;
import com.hccake.ballcat.common.model.domain.PageResult;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
