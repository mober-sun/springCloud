package com.sunhui.order_service.fallBack;

import com.sunhui.order_service.service.ProductClient;
import org.springframework.stereotype.Component;

// feign 实现类交给 spring 管理

/**
 * feign 结合hystrix
 * 1、feign配置文件,开启对hystrix 的支持
 * 2、此类比较实现 feign定义的类
 */
@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public String findById(int id) {
        // 可以做其他的处理
        System.out.println(" fallBack 异常处理。。。。。。");
        return null;
    }
}


