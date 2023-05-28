package org.ballcat.business.log.converter;

import org.ballcat.business.log.model.entity.OperationLog;
import org.ballcat.business.log.model.vo.OperationLogPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 操作日志模型转换器
 *
 * @author hccake 2021-03-22 20:32:30
 */
@Mapper
public interface OperationLogConverter {

	OperationLogConverter INSTANCE = Mappers.getMapper(OperationLogConverter.class);

	/**
	 * PO 转 PageVO
	 * @param operationLog 操作日志
	 * @return AdminOperationLogPageVO 操作日志PageVO
	 */
	OperationLogPageVO poToPageVo(OperationLog operationLog);

}
