package com.kalman03.easypay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kalman03
 * @since 2023-05-20
 */
@Controller
public class IndexController {

	@RequestMapping({ "/index", "index.html", "", "/" })
	public String index() {
		return "index";
	}
}
