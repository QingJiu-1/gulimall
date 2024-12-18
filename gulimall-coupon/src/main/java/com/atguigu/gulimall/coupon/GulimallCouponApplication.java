package com.atguigu.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 1、如何使用nacos作为配置中心作为配置中心统一管理配置
 *  映入依赖：
 *  <!--        配置中心来做配置管理-->
 *         <dependency>
 *             <groupId>com.alibaba.cloud</groupId>
 *             <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *         </dependency>
 *  创建一个bootstrap.properties配置文件
 *      spring.application.name=gulimall-coupon
 *      spring.cloud.nacos.config.server-addr=127.0.0.1:8848
 *  需要给配置中心默认添加一个叫 数据集（Data Id）gulimall-coupon.properties 默认规则，就是应用名加.properties
 *  给 应用名加.properties 添加任何配置
 *  动态获取配置：
 *      @RefreshScope:  动态获取刷新配置
 *      @Value("${配置项的名}")
 *      如果配置中心和当前应用的配置文件中都配置了相同的项，优先使用配置中心的配置
 *2、细节
 *  命名空间：配置隔离
 *      默认：public(保留空间)；默认新增的所有配置都在public空间。
 *      1）开发、测试、生产：利用命名空间来做环境隔离。
 *      注意：在bootstrap.properties:配置上，需要使用哪个命名空间下的配置。
 *      spring.cloud.nacos.config.namespace=67727089-ccb2-4992-aa52-13cad83650b7(唯一ID)
 *      2）每一个微服务之间互相隔离配置，每一个微服务都创建自己的命名空间，只加载自己命名空间下的所有配置
 *
 *  配置集：所有的配置的集合
 *
 *  配置集ID：类似文件名。
 *      Data ID:类似文件名
 *
 *  配置分组：
 *      默认所有的配置集都属于：DEFAULT_GROUP；
 *      1111，618，1212
 *      spring.cloud.nacos.config.group=1111;若是不标明则默认为DEFAULT_GROUP组的
 *
 *每个微服务创建自己的命名空间，使用配置分组区分环境：dev test prod
 *
 *3、同时加载多个配置集
 *  微服务任何配置信息，任何配置文件都可以放在配置中心中
 *  只需要在bootstrap.properties 说明加载配置文件中获取值，都能使用
 *  @Value、@ConfigurationProperties....
 *      以前SpringBoot任何方法从配置文件中获取值，都能使用。
 *  配置中心有的优先使用配置中心中的，使用方法。
 *
 *
 */

@EnableDiscoveryClient
@SpringBootApplication
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
