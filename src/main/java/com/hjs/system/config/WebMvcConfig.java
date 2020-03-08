package com.hjs.system.config;

import com.mysql.jdbc.jmx.LoadBalanceConnectionGroupManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/3 22:08
 * @Modified By:
 */

/**
 * 自定义静态资源配置, 解决springboot2.x无法默认访问静态资源的问题
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");

    }


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//
//    }
}
