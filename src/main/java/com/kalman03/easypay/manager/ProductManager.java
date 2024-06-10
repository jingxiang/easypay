package com.kalman03.easypay.manager;

import com.kalman03.common.mybatis.CrudManager;

import com.kalman03.easypay.biz.domain.ProductQueryDO;
import com.kalman03.easypay.biz.domain.ProductDO;
import com.kalman03.easypay.dao.pojo.Product;

/**
 * @since 2024-05-25
 * @author kalman03
 */
public interface ProductManager extends CrudManager<ProductDO,Product, ProductQueryDO>{

}
