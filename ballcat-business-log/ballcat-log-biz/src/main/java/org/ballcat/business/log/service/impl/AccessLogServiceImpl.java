package org.ballcat.business.log.service.impl;

import org.ballcat.business.log.mapper.AccessLogMapper;
import org.ballcat.business.log.model.entity.AccessLog;
import org.ballcat.business.log.model.qo.AccessLogQO;
import org.ballcat.business.log.model.vo.AccessLogPageVO;
import org.ballcat.business.log.service.AccessLogService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 后台访问日志
 *
 * @author hccake 2019-10-16 16:09:25
 */
@Slf4j
@Service
public class AccessLogServiceImpl extends ExtendServiceImpl<AccessLogMapper, AccessLog> implements AccessLogService {

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return IPage<LoginLogVO> 分页数据
	 */
	@Override
	public PageResult<AccessLogPageVO> queryPage(PageParam pageParam, AccessLogQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

}
