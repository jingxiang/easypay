package com.kalman03.easypay.utils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author kalman03
 * @since 2023-01-01
 */
public class WebUtils {

	public static String getRedirectUrl(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		Map<String, String[]> params = request.getParameterMap();
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				builder.append(key).append("=").append(value).append("&");
			}
		}
		String queryStr = builder.toString();
		if (isNotBlank(queryStr)) {
			queryStr = queryStr.substring(0, queryStr.length() - 1);
		}
		String redirectUrl = "";
		if (isNotBlank(queryStr)) {
			redirectUrl = request.getRequestURL() + "?" + queryStr;
		} else {
			redirectUrl = request.getRequestURL().toString();
		}
		try {
			return URLEncoder.encode(redirectUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return redirectUrl;
	}

	public static String getQueryStringExceptKeys(HttpServletRequest request, String... keys) {
		if (keys == null) {
			return request.getQueryString();
		}
		List<String> keyList = Arrays.asList(keys);
		StringBuilder builder = new StringBuilder();
		Map<String, String[]> params = request.getParameterMap();
		for (String key : params.keySet()) {
			if (keyList.contains(key)) {
				continue;
			}
			String[] values = params.get(key);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				builder.append(key).append("=").append(value).append("&");
			}
		}
		return builder.toString();
	}
}
