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
 *  分组校验(可以完成多场景的复杂校验)：
 *      在所添加的校验注解中添加groups，表明什么情况下需要进行校验；
 *      注意：未标明的分组的校验规则，在分组校验下默认是不生效的，只会在@Validated下生效
 *      在控制层将开启校验注解的@Valid修改为@Validated({})，可以指定其校验规则
 *  自定义校验
 *      编写一个自定义的校验注解
 *      编写一个自定义的校验器 必须实现ConstraintValidator接口
 *      关联自定义的校验器和自定义的校验规则
 *      @Documented
 *      @Constraint(validatedBy = { ListValueConstraintValidator.class【可以指定多个不同的校验器，适配不同类型的校验】 }) //指定自定义校验器
 *      @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
 *      @Retention(RUNTIME)
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
