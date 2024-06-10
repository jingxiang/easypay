package com.kalman03.easypay.service;

import static com.google.common.collect.Maps.newHashMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.kalman03.common.api.result.Result;
import com.kalman03.common.utils.BeanUtils;
import com.kalman03.common.utils.CalendarUtils;
import com.kalman03.common.utils.RandomUtils;
import com.kalman03.common.weixin.domain.WeixinConfig;
import com.kalman03.common.weixin.domain.WeixinConfig.PayConfig;
import com.kalman03.common.weixin.domain.WeixinPayParams;
import com.kalman03.common.weixin.req.pay.BaseTransactionsRequest.Amount;
import com.kalman03.common.weixin.req.pay.BaseTransactionsRequest.Payer;
import com.kalman03.common.weixin.req.pay.TransactionsJsapiRequest;
import com.kalman03.common.weixin.resp.pay.TransactionsIdResponse;
import com.kalman03.common.weixin.service.WeixinPayService;
import com.kalman03.easypay.biz.domain.PayDO;
import com.kalman03.easypay.biz.domain.PayQueryDO;
import com.kalman03.easypay.biz.domain.ProductDO;
import com.kalman03.easypay.common.BizRuntimeException;
import com.kalman03.easypay.constants.PayStatus;
import com.kalman03.easypay.constants.SystemConstant;
import com.kalman03.easypay.dao.pojo.Pay;
import com.kalman03.easypay.manager.PayManager;
import com.kalman03.easypay.manager.ProductManager;
import com.kalman03.easypay.web.dto.WeixinPayCallbackReq;
import com.kalman03.easypay.web.dto.WeixinPayCallbackReq.WechatPayResource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author kalman03
 * @since 2024-05-25
 */
@Slf4j
@Service
public class PayService {
	@Autowired
	private WeixinPayService weixinPayService;
	@Autowired
	private WeixinConfig weixinConfig;
	@Autowired
	private ProductManager productManager;
	@Autowired
	private PayManager payManager;

	@Transactional(noRollbackFor = Exception.class)
	public WeixinPayParams getWeixinPayParams(Long productId, String openId) {
		String outOrderId = CalendarUtils.format(new Date(), "yyyyMMddHHmmss") + ""
				+ RandomUtils.randomNumber(1000000, 9999999);
		ProductDO productDO = productManager.findById(productId);
		Pay pay = new Pay();
		pay.setAmount(productDO.getAmount());
		pay.setPayAmount(productDO.getAmount());
		pay.setPayId(outOrderId);
		pay.setPayStatus(PayStatus.WAIT_PAY.getStatus());
		pay.setProductId(productId);
		pay.setOpenId(openId);
		payManager.save(pay);

		PayConfig payConfig = weixinConfig.getPayConfigMap(SystemConstant.MCH_ID);
		TransactionsJsapiRequest jsapiRequest = new TransactionsJsapiRequest();
		jsapiRequest.setAppid(SystemConstant.APP_ID);
		jsapiRequest.setMchid(SystemConstant.MCH_ID);
		jsapiRequest.setDescription(productDO.getTitle());
		jsapiRequest.setOut_trade_no(outOrderId);
		jsapiRequest.setNotify_url(payConfig.getPayNotifyUrl());
		Amount amount = new Amount();
		amount.setCurrency("CNY");
		amount.setTotal(productDO.getAmount().multiply(new BigDecimal("100")).longValue());
		jsapiRequest.setAmount(amount);
		Payer payer = new Payer();
		payer.setOpenid(openId);
		jsapiRequest.setPayer(payer);
		Result<WeixinPayParams> result = weixinPayService.getWeixinPayParams(SystemConstant.APP_ID, jsapiRequest);
		if (result.getCode() != Result.SUCCESS) {
			throw new BizRuntimeException(result.getMessage());
		}
		return result.getData();
	}

	public String doWeixinCallback(WeixinPayCallbackReq payNotify) {
		log.info("Weixin callback body:{}", JSON.toJSONString(payNotify));
		if (!"TRANSACTION.SUCCESS".equals(payNotify.getEvent_type()) || payNotify.getResource() == null) {
			log.error("weixin pay callback resource is null");
			return getErrorMessage();
		}
		try {
			PayConfig payConfig = weixinConfig.getPayConfigMap(SystemConstant.MCH_ID);
			String apiV3Key = payConfig.getApiV3Key();
			WechatPayResource resource = payNotify.getResource();
			String data = aead_aes_256_gcm(resource.getNonce(), resource.getAssociated_data(), resource.getCiphertext(),
					apiV3Key);
			TransactionsIdResponse result = JSON.parseObject(data, TransactionsIdResponse.class);
			Date successTime = CalendarUtils.parse(result.getSuccess_time(), "yyyy-MM-dd'T'HH:mm:ssXXX");
			doPayStatusUpdate(result.getOut_trade_no(), result.getTransaction_id(), successTime);
			
//			TransactionsIdRequest transactionsIdRequest = new TransactionsIdRequest();
//			transactionsIdRequest.setMchid(SystemConstant.MCH_ID);
//			transactionsIdRequest.setTransaction_id(idResponse.getTransaction_id());
//			TransactionsIdResponse result = weixinPayService.excute(SystemConstant.APP_ID, transactionsIdRequest);
//			if (result != null && "SUCCESS".equals(result.getTrade_state())) {
//				Date successTime = CalendarUtils.parse(result.getSuccess_time(), "yyyy-MM-dd'T'HH:mm:ssXXX");
//				doPayStatusUpdate(result.getOut_trade_no(), result.getTransaction_id(), successTime);
//			} else {
//				log.info("微信后台无法查询到当前订单, weixinPayService.queryByTransactionId result:{}", JSON.toJSONString(result));
//				return getErrorMessage();
//			}
			Map<String, Object> map = newHashMap();
			map.put("code", "SUCCESS");
			map.put("message", "");
			return JSON.toJSONString(map);
		} catch (Exception e) {
			log.error("", e);
			return getErrorMessage();
		}
	}

	private String getErrorMessage() {
		Map<String, Object> map = newHashMap();
		map.put("code", "FAIL");
		map.put("message", "失败");
		return JSON.toJSONString(map);
	}

	private void doPayStatusUpdate(String outTradeNo, String trade_no, Date gmt_payment) {
		PayQueryDO payQueryDO = new PayQueryDO();
		payQueryDO.setPayId(outTradeNo);
		PayDO openPayDO = payManager.findAll(payQueryDO).stream().findFirst().orElse(null);
		if (openPayDO == null) {
			log.warn("根据外部订单号找不到待支付记录，outTradeNo={}", outTradeNo);
			throw new BizRuntimeException("找不到支付宝支付回调数据");
		}
		if (openPayDO.getPayStatus() == PayStatus.PAID.getStatus()) {
			log.info("支付回调感觉重复了，数据库支付状态显示已经成功，忽略消息。outTradeNo={}", outTradeNo);
			return;
		}
		Pay target = new Pay();
		BeanUtils.copyProperties(openPayDO, target);
		target.setPayTime(gmt_payment);
		target.setPayStatus(PayStatus.PAID.getStatus());
		target.setTransactionId(trade_no);
		int rows = payManager.update(target);
		Preconditions.checkState(rows > 0, "未能找到对应的预支付记录");
	}

	/**
	 * AEAD_AES_256_GCM算法
	 */
	private static String aead_aes_256_gcm(String nonce, String associate, String ciphertext, String apiv3signkey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		SecretKeySpec key = new SecretKeySpec(apiv3signkey.getBytes(), "AES");
		GCMParameterSpec spec = new GCMParameterSpec(128, nonce.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, key, spec);
		cipher.updateAAD(associate.getBytes());
		return new String(cipher.doFinal(Base64.decodeBase64(ciphertext)), "utf-8");
	}
}
