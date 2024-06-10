package com.kalman03.easypay.utils;

import static org.apache.commons.lang3.StringUtils.isBlank;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author kalman03
 * @since 2022-09-23
 */
public class IpUtils {

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (isBlank(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
