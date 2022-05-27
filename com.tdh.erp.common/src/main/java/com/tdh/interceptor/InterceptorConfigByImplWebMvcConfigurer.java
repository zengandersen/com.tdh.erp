package com.tdh.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfigByImplWebMvcConfigurer implements WebMvcConfigurer {
	
	@Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
	 @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("api/**","/static/**","/api/**","/css/**","/images/**","/js/**","/lib/**","/layui/**","/H-ui-admin/**","/loginReq.do","/login.do","/layer/**");

    }
}
