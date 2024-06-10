package com.kalman03.easypay.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author kalman03
 * @since 2024-05-25
 */
@AllArgsConstructor
@Getter
public enum PayStatus {

	WAIT_PAY(0, "待支付"), PAID(1, "已支付");

	private int status;
	private String desc;
}
