package com.kalman03.easypay.common;

/**
 * @author kalman03
 * @since 2022-12-17
 */
public class SpecialCodeException extends RuntimeException {

	private static final long serialVersionUID = 9068264251200063018L;

	public enum SpecialCode {
		NEED_LOGIN(401, "请先登录"), SUBSCRIBE_VALID(403, "您的订阅已到期，请续费以继续使用"),;

		private int code;
		private String message;

		private SpecialCode(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	private final SpecialCode specialCode;

	public SpecialCodeException(SpecialCode specialCode) {
		this.specialCode = specialCode;
	}

	public SpecialCode getSpecialCode() {
		return specialCode;
	}
}
