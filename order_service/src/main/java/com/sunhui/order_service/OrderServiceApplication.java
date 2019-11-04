package com.sunhui.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients     // 1、feign 模拟接口的方式。底层实现了ribbon可以实现 负载均衡
@EnableCircuitBreaker   // 开启hystrix 的支持； 注解越来越多 可以使用@SpringCloudApplication
@EnableHystrixDashboard  // 3、断路器仪表盘
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }


   /* 分布式服务之间调用方式：
   *
   * 1、RPC 方式调用
   * 2、基于 Rest 的方式
   *
   * */

   /* 提供 rest 方式调用*/

    @Bean   // java 注解的方式注入bean;
    @LoadBalanced    // ribbon (类似于httpclient urlConnection 负载均衡的去调用)
    public RestTemplate getRestTemplate(){
        // 说明这个rest 调用的方式可以实现 负载均衡的调用 服务；
        return new RestTemplate();
    }

}
