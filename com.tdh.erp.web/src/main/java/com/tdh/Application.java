package com.tdh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: zengqw
 * @Description: 启动类
 * @Date: Created in 12:05 2021-11-09
 * @Modified by:
 */
@SpringBootApplication // 扫描所有包
// 启动类
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.tdh"})
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



}
