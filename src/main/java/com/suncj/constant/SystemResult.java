package com.suncj.constant;

import com.suncj.base.BaseResult;

/**
 * 系统返回结果包装类
 * 
 * @author scj
 *
 */
public class SystemResult extends BaseResult {

	public SystemResult(ResultConstant resultConstant, Object data) {
		super(resultConstant.getCode(), resultConstant.getMessage(), data);
	}

}