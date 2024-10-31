package com.atiguigu.gulimall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 处理跨域的config
 */
@Configuration
public class GulimallCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //1、在corsConfiguration中配置跨域
        corsConfiguration.addAllowedHeader("*"); //任意请求头
        corsConfiguration.addAllowedMethod("*"); //任意请求方式
        corsConfiguration.addAllowedOrigin("*"); //任意请求来源
        corsConfiguration.setAllowCredentials(true); //允许携带Coke信息

        source.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsWebFilter(source);

    }
}
