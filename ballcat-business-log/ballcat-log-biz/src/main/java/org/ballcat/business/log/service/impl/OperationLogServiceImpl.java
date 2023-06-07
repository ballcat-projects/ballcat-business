package org.ballcat.business.log.service.impl;

import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.business.log.mapper.OperationLogMapper;
import org.ballcat.business.log.model.entity.OperationLog;
import org.ballcat.business.log.model.qo.OperationLogQO;
import org.ballcat.business.log.model.vo.OperationLogPageVO;
import org.ballcat.business.log.service.OperationLogService;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 操作日志
 *
 * @author hccake
 * @date 2019-10-15 20:42:32
 */
@Service
public class OperationLogServiceImpl extends ExtendServiceImpl<OperationLogMapper, OperationLog>
		implements OperationLogService {

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<LoginLogVO> 分页数据
	 */
	@Override
	public PageResult<OperationLogPageVO> queryPage(PageParam pageParam, OperationLogQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

	/**
	 * 异步保存操作日志
	 * @param operationLog 操作日志
	 */
	@Async
	@Override
	public void saveAsync(OperationLog operationLog) {
		baseMapper.insert(operationLog);
	}

}
