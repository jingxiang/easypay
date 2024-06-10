package com.kalman03.easypay.biz.domain;

import com.kalman03.common.lang.PageQueryDO;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @since 2024-05-25
 * @author kalman03
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductQueryDO extends PageQueryDO{
	private static final long serialVersionUID = 1L;
	/** 
	 *	商品ID 
	 */
	private Long id;
	/** 
	 *	商品价格 
	 */
	private BigDecimal amount;
	/** 
	 *	商品标题 
	 */
	private String title;
	/** 
	 *	商品简介 
	 */
	private String summary;
	/** 
	 *	商品描述 
	 */
	private String description;
	/** 
	 *	创建时间 
	 */
	private Date createTime;
	/** 
	 *	更新时间 
	 */
	private Date updateTime;
	/**
	 * 排序，如：create_time desc
	 */
	private String orderBy;
}