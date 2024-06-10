package com.kalman03.easypay.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kalman03.common.api.result.Result;
import com.kalman03.common.weixin.domain.WeixinPayParams;
import com.kalman03.easypay.manager.ProductManager;
import com.kalman03.easypay.service.PayService;
import com.kalman03.easypay.web.ThreadLocalContext;
import com.kalman03.easypay.web.dto.PayDTO;
import com.kalman03.easypay.web.dto.WeixinPayCallbackReq;

/**
 * @author kalman03
 * @since 2024-05-25
 */
@Controller
public class PayController {

	@Autowired
	private ProductManager productManager;
	@Autowired
	private PayService payService;

	@GetMapping("/pay/index")
	public String pay(Long id, Model model) {
		model.addAttribute("item", productManager.findById(id));
		return "pay";
	}

	@PostMapping("/pay/weixin_pay_params")
	public @ResponseBody Result<WeixinPayParams> doPay(PayDTO payDTO) {
		WeixinPayParams weixinPayParams = payService.getWeixinPayParams(payDTO.getProductId(),
				ThreadLocalContext.getOpenId());
		return Result.ok(weixinPayParams);
	}

	@RequestMapping("/pay/weixin_callback")
	public @ResponseBody String weixinCallback(@RequestBody WeixinPayCallbackReq callbackReq) {
		return payService.doWeixinCallback(callbackReq);
	}
}
