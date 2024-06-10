package com.kalman03.easypay.manager.impl;

import org.springframework.stereotype.Service;

import com.kalman03.common.mybatis.AbstractCrudManager;

import com.kalman03.easypay.biz.domain.ProductQueryDO;
import com.kalman03.easypay.biz.domain.ProductDO;
import com.kalman03.easypay.manager.ProductManager;
import com.kalman03.easypay.dao.mapper.ProductMapper;
import com.kalman03.easypay.dao.pojo.Product;

/**
 * @since 2024-05-25
 * @author kalman03
 */
@Service
public class ProductManagerImpl extends AbstractCrudManager<ProductDO,Product, ProductQueryDO, ProductMapper> implements ProductManager {

}
