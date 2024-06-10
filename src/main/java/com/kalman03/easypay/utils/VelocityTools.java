package com.kalman03.easypay.utils;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.apache.commons.text.StringEscapeUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author kalman03
 * @since 2022-09-06
 */
public class VelocityTools {

	private final HttpServletRequest request;

	public VelocityTools(HttpServletRequest request) {
		this.request = request;
	}

	public static String getAssets(String file) {
		if (!file.startsWith("/")) {
			file = "/" + file;
		}
		return file;
	}

	public Object getSessionValue(String sessionkey) {
		return request.getSession().getAttribute(sessionkey);
	}

	public String getRequestValue(String name) {
		return request.getParameter(name);
	}

	public String escapeHTML(String html) {
		if (isBlank(html)) {
			return html;
		}
		return StringEscapeUtils.escapeHtml4(html);
	}

	public String getShortString(String str, int num) {
		if (isBlank(str)) {
			return str;
		}
		int index = Math.min(num, str.length());

		str = str.substring(0, index);
		if (str.length() > num) {
			str += "...";
		}
		return str;
	}
}