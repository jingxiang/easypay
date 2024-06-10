package com.kalman03.easypay.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.kalman03.common.mybatis.CrudMapper;

import com.kalman03.easypay.dao.pojo.Product;

/**
 * @since 2024-05-25
 * @author kalman03
 */
@Mapper
public interface ProductMapper extends CrudMapper<Product>{

}