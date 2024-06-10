package com.kalman03.easypay.common;

/**
 * @author kalman03
 * @since 2022-09-22
 */
public class BizRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5771597094636092533L;

	private final String message;

	public BizRuntimeException(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMessage() {
		return message;
	}

}
