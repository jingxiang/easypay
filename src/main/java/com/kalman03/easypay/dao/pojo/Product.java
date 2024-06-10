package com.kalman03.easypay.dao.pojo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @since 2024-05-25
 * @author kalman03
 */
@Data
public class Product{

	/** 
	 * 商品ID 
	 * product.id
	 */
	private Long id;
	/** 
	 * 商品价格 
	 * product.amount
	 */
	private BigDecimal amount;
	/** 
	 * 商品标题 
	 * product.title
	 */
	private String title;
	/** 
	 * 商品简介 
	 * product.summary
	 */
	private String summary;
	/** 
	 * 商品描述 
	 * product.description
	 */
	private String description;
	/** 
	 * 创建时间 
	 * product.create_time
	 */
	private Date createTime;
	/** 
	 * 更新时间 
	 * product.update_time
	 */
	private Date updateTime;

}