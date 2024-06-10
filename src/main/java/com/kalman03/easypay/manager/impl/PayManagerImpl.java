package com.kalman03.easypay.manager.impl;

import org.springframework.stereotype.Service;

import com.kalman03.common.mybatis.AbstractCrudManager;

import com.kalman03.easypay.biz.domain.PayQueryDO;
import com.kalman03.easypay.biz.domain.PayDO;
import com.kalman03.easypay.manager.PayManager;
import com.kalman03.easypay.dao.mapper.PayMapper;
import com.kalman03.easypay.dao.pojo.Pay;

/**
 * @since 2024-05-26
 * @author kalman03
 */
@Service
public class PayManagerImpl extends AbstractCrudManager<PayDO,Pay, PayQueryDO, PayMapper> implements PayManager {

}
