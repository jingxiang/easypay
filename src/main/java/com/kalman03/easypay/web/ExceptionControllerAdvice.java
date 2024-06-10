package com.kalman03.easypay.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.kalman03.easypay.common.BizRuntimeException;
import com.kalman03.easypay.utils.VelocityTools;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {


	@ExceptionHandler(value = { Exception.class })
	public ModelAndView error(Exception ex, HttpServletRequest request, Model model) {
		VelocityTools velocityTools = new VelocityTools(request);
		boolean isJsonRequest = isJsonRequest(request);
		if (ex instanceof BizRuntimeException) {
			BizRuntimeException exception = (BizRuntimeException) ex;
			String errorMessage = exception.getMessage();
			log.error("BizRuntimeException--->{}",errorMessage);
			return isJsonRequest ? createJsonView(errorMessage, velocityTools)
					: createWebView(errorMessage, velocityTools);
		}
		log.error("Exception--->{}", ex);
		return createNormalView(isJsonRequest, velocityTools);
	}

	private boolean isJsonRequest(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/api");
	}

	private ModelAndView createNormalView(boolean isJsonRequest, VelocityTools velocityTools) {
		String errorMessage = "系统错误，请稍后再试。";
		if (isJsonRequest) {
			return createJsonView(errorMessage, velocityTools);
		}
		return createWebView(errorMessage, velocityTools);
	}

	private ModelAndView createWebView(String errorMessage, VelocityTools velocityTools) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", errorMessage);
		mav.addObject("velocity", velocityTools);
		mav.setViewName("error");
		return mav;
	}

	private ModelAndView createJsonView(int code, String errorMessage, VelocityTools velocityTools) {
		ModelAndView mav = new ModelAndView();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", code);
		result.put("message", errorMessage);
//		result.put("velocity", velocityTools);
		view.setAttributesMap(result);
		mav.setView(view);
		return mav;
	}

	private ModelAndView createJsonView(String errorMessage, VelocityTools velocityTools) {
		return createJsonView(-1, errorMessage, velocityTools);
	}
}