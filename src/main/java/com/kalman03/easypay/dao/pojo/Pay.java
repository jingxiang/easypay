package com.kalman03.easypay.dao.pojo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @since 2024-05-26
 * @author kalman03
 */
@Data
public class Pay{

	/** 
	 * 订单id 
	 * pay.id
	 */
	private Long id;
	/** 
	 * openid 
	 * pay.open_id
	 */
	private String openId;
	/** 
	 * 商品Id 
	 * pay.product_id
	 */
	private Long productId;
	/** 
	 * 商品价格 
	 * pay.amount
	 */
	private BigDecimal amount;
	/** 
	 * 实际支付价格 
	 * pay.pay_amount
	 */
	private BigDecimal payAmount;
	/** 
	 * 支付状态，0：待支付，1：已支付 
	 * pay.pay_status
	 */
	private Integer payStatus;
	/** 
	 * 支付时间 
	 * pay.pay_time
	 */
	private Date payTime;
	/** 
	 * 外部支付平台ID 
	 * pay.transaction_id
	 */
	private String transactionId;
	/** 
	 * 本平台的支付唯一ID 
	 * pay.pay_id
	 */
	private String payId;
	/** 
	 * 创建时间 
	 * pay.create_time
	 */
	private Date createTime;
	/** 
	 * 更新时间 
	 * pay.update_time
	 */
	private Date updateTime;

}