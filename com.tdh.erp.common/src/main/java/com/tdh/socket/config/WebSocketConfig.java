package com.tdh.socket.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 如果使用tomcat 开发 需要将ConditionalOnProperty 注解放开 进入开发环境
 * 还需要将Socket 文件中的注解//@ConditionalOnClass 和 @Component 注解内容放开
 *  TODO 问提由springboot 内置tomcat 和 tomcat 容器自身扫描机制不同 导致 原生版本不能再tomcat容器下正常运行
 */
//@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
