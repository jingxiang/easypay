package com.kalman03.easypay.configuration;

import static com.google.common.collect.Lists.newArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kalman03.easypay.interceptor.VelocityModelInterceptor;
import com.kalman03.easypay.interceptor.WeixinLoginInterceptor;

/**
 * @author kalman03
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	@Autowired
	private VelocityModelInterceptor velocityModelInterceptor;
	@Autowired
	private WeixinLoginInterceptor weixinLoginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(weixinLoginInterceptor).addPathPatterns("/**").excludePathPatterns("/wx_login.html",
				"/MP_verify_hVTVISdtutaW4Zlz.txt", "/pay/weixin_callback", "/","/index");
		registry.addInterceptor(velocityModelInterceptor).addPathPatterns("/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true)
				.allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS").maxAge(3600);
	}

	private CorsConfiguration addcorsConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(newArrayList("*"));
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		return corsConfiguration;
	}

	@Bean
	CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", addcorsConfig());
		return new CorsFilter(source);
	}

}
