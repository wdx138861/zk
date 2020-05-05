package com.example.springboot.configuration;

import com.example.springboot.interceptor.SomeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: wdx
 * @Data: 2020/4/27 23:01
 * @Configuration 配置文件注解
 */
@Configuration
public class MyWebMvcConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        SomeInterceptor someInterceptor = new SomeInterceptor();
        // 添加拦截器
        registry.addInterceptor(someInterceptor)
                // 拦截first开头
                .addPathPatterns("/first/**")
                // 不拦截second开头
                .excludePathPatterns("/second/**");
    }
}
