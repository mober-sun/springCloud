package com.sunhui.order_service.service;


import com.sunhui.order_service.fallBack.ProductClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 1、productService 的客户端
 *
 * // name 解释其他注册到eureka 上的服务名称；
 */
//@FeignClient(name="product-service" )  // feign的标准用法

/**
 *  2、一旦有了 fallback 两层含义：1、feign 里面默认有hysteria；开启（手动配置文件添加信息）；2、下级报错。熔断会自动进入 fallback的类里面去，执行熔断后的方法；
 */
@FeignClient(name="product-service" ,fallback = ProductClientFallback.class)
public interface ProductClient {


    /**
     * 也可以使用@requestMapping("") 注解 功能一样；
     *注意点：
     * 			1、路径 // 1、服务名称（上面的name） 2、服务请求路径 必须一致;
     * 			2、Http方法必须对应(get/post)
     * 			3、使用requestBody，应该使用@PostMapping
     * 			4、多个参数的时候，通过@RequestParam（"id") int id)方式调用
     */
    @GetMapping("/api/v1/product/find") // 服务提供方的 请求路径
    String findById(@RequestParam("id") int id);
}
