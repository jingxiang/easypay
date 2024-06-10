package com.kalman03.easypay.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.kalman03.common.utils.RandomUtils;
import com.kalman03.easypay.constants.SystemConstant;
import com.kalman03.easypay.utils.WebUtils;
import com.kalman03.easypay.web.SessionUser;
import com.kalman03.easypay.web.ThreadLocalContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author kalman03
 * @since 2022-09-06
 */
@Component
public class WeixinLoginInterceptor implements HandlerInterceptor {

	@Value("${project.base.url}")
	private String url;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object object = request.getSession().getAttribute("sessionUser");
		if (object == null) {
			String url = getWeixinLoginUrl(request);
			response.sendRedirect(url);
			return false;
		}
		SessionUser sessionUser = JSON.parseObject(object.toString(), SessionUser.class);
		ThreadLocalContext.setSessionUser(sessionUser);
		return true;
	}

	private String getWeixinLoginUrl(HttpServletRequest request) throws UnsupportedEncodingException {
		String redirectUrl = WebUtils.getRedirectUrl(request);
		String _url = url + "/wx_login.html?redirectUrl=" + redirectUrl;
		_url = URLEncoder.encode(_url, "utf-8");
		String state = RandomUtils.randomString(5);
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + SystemConstant.APP_ID + "&redirect_uri="
				+ _url + "&response_type=code&scope=snsapi_base&state=" + state + "#wechat_redirect";
	}
}
