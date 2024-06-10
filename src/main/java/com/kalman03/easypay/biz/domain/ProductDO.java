package com.kalman03.easypay.biz.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @since 2024-05-25
 * @author kalman03
 */
@Data
public class ProductDO{

	/** 
	 * 商品ID 
	 */
	private Long id;
	/** 
	 * 商品价格 
	 */
	private BigDecimal amount;
	/** 
	 * 商品标题 
	 */
	private String title;
	/** 
	 * 商品简介 
	 */
	private String summary;
	/** 
	 * 商品描述 
	 */
	private String description;
	/** 
	 * 创建时间 
	 */
	private Date createTime;
	/** 
	 * 更新时间 
	 */
	private Date updateTime;
	
	public String getAmountStr() {
		String str= amount.toString();
		while(str.endsWith("0")||str.endsWith(".")) {
			str= str.substring(0,str.length()-1);
		}
		return str;
	}

}