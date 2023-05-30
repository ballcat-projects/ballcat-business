package org.ballcat.business.infra.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * 字典修改事件
 *
 * @author Hccake 2021/1/5
 * @version 1.0
 */
@Getter
@ToString
public class DictChangeEvent extends ApplicationEvent {

	private final String dictCode;

	public DictChangeEvent(String dictCode) {
		super(dictCode);
		this.dictCode = dictCode;
	}

}
