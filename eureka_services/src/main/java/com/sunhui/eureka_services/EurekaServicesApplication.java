package com.sunhui.eureka_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication    // @SpringBootApplication = (默认属性)@Configuration + @EnableAutoConfiguration + @ComponentScan。
@EnableEurekaServer
public class EurekaServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServicesApplication.class, args);
    }

}
