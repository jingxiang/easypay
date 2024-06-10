package com.kalman03.easypay.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.kalman03.common.weixin.domain.WeixinConfig;
import com.kalman03.common.weixin.domain.WeixinConfig.MpConfig;
import com.kalman03.common.weixin.req.mp.SnsAuth2AccessTokenRequest;
import com.kalman03.common.weixin.resp.mp.SnsAuth2AccessTokenResponse;
import com.kalman03.common.weixin.service.WeixinMpService;
import com.kalman03.easypay.web.SessionUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kalman03
 * @since 2024-05-25
 */
@Slf4j
@Controller
public class UserSecurityController {

	@Autowired
	private WeixinMpService weixinMpService;
	@Autowired
	private WeixinConfig weixinConfig;

	@RequestMapping("/wx_login.html")
	public String login(HttpServletRequest request, String code, String redirectUrl) {
		MpConfig mpConfig = weixinConfig.getMpConfig().get(0);
		SnsAuth2AccessTokenRequest accessTokenRequest = new SnsAuth2AccessTokenRequest();
		accessTokenRequest.setAppid(mpConfig.getAppId());
		accessTokenRequest.setCode(code);
		accessTokenRequest.setGrant_type("authorization_code");
		accessTokenRequest.setSecret(mpConfig.getAppSecret());
		SnsAuth2AccessTokenResponse accessTokenResponse = weixinMpService.excute(mpConfig.getAppId(),
				accessTokenRequest);
		if (!accessTokenResponse.isSuccess()) {
			log.error("accessTokenResponse error,accessTokenResponse={}", JSON.toJSONString(accessTokenResponse));
		}
		String openId = accessTokenResponse.getOpenid();
		SessionUser sessionUser = new SessionUser();
		sessionUser.setOpenId(openId);
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("sessionUser", JSON.toJSONString(sessionUser));
		int defaultExpireTime = 60 * 60 * 2;// 2小时
		httpSession.setMaxInactiveInterval(defaultExpireTime);
		return "redirect:" + redirectUrl;
	}
}
