package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * JSR303:
 *  给需要的校验的数据加上注解，给Bean添加注解：javax.validation.constraints，并定义自己的message提示
 *  只标注校验规则，不在控制层，开启校验规则也是不起作用 ;开启校验@Valid:
 * 	    效果：校验错误会有默认的响应：
 *  给校验的bean后紧跟一个BindingResult,就可以获取到校验结果
 *
 * 统一的异常处理
 *  使用springMVC提供的 @ControllerAdvice
 *      抽取异常处理类
 */
@MapperScan("com.atguigu.gulimall.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
