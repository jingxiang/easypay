package com.kalman03.easypay.manager;

import com.kalman03.common.mybatis.CrudManager;

import com.kalman03.easypay.biz.domain.PayQueryDO;
import com.kalman03.easypay.biz.domain.PayDO;
import com.kalman03.easypay.dao.pojo.Pay;

/**
 * @since 2024-05-26
 * @author kalman03
 */
public interface PayManager extends CrudManager<PayDO,Pay, PayQueryDO>{

}
