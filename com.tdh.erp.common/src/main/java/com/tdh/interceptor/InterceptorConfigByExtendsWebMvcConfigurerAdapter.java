package com.tdh.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class InterceptorConfigByExtendsWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	
	//@Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
	
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
    }
}
