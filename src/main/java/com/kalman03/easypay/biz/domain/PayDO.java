package com.kalman03.easypay.biz.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @since 2024-05-26
 * @author kalman03
 */
@Data
public class PayDO{

	/** 
	 * 订单id 
	 */
	private Long id;
	/** 
	 * openid 
	 */
	private String openId;
	/** 
	 * 商品Id 
	 */
	private Long productId;
	/** 
	 * 商品价格 
	 */
	private BigDecimal amount;
	/** 
	 * 实际支付价格 
	 */
	private BigDecimal payAmount;
	/** 
	 * 支付状态，0：待支付，1：已支付 
	 */
	private Integer payStatus;
	/** 
	 * 支付时间 
	 */
	private Date payTime;
	/** 
	 * 外部支付平台ID 
	 */
	private String transactionId;
	/** 
	 * 本平台的支付唯一ID 
	 */
	private String payId;
	/** 
	 * 创建时间 
	 */
	private Date createTime;
	/** 
	 * 更新时间 
	 */
	private Date updateTime;

}