package com.sunhui.order_service.controller;

import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sunhui.order_service.service.ProductOrderService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;  //2、springboot 集成redis后使用；

    //@Autowired
    //private ProductClient productClient;  // feign 创造的接口类；





    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")   // 1、熔断时 有错误 调用的方法
    public Object save(@RequestParam("user_id")int userId, @RequestParam("product_id") int productId) throws IOException {

        HashMap<String, Object> data = Maps.newHashMap();
        data.put("code",0);
        data.put("data",productOrderService.save(userId, productId));
        return data;
    }


    private Object saveOrderFail(int userId,int productId){
         String saveOrderKye = "save-order";
        String s = stringRedisTemplate.opsForValue().get(saveOrderKye);

        new Thread(()->{
            if(StringUtils.isBlank(s)){
                System.out.println("紧急短信，用户下单失败，请离开查找原因");
                //发送一个http请求，调用短信服务 TODO
                stringRedisTemplate.opsForValue().set(saveOrderKye, "save-order-fail", 20, TimeUnit.SECONDS);
            }else{
                System.out.println("已经发送过短信，20秒内不重复发送");
            }
        }).start();

        Map<String, Object> msg = new HashMap<>();
        msg.put("code", -1);
        msg.put("msg", "抢购人数太多，您被挤出来了，稍等重试");
        return msg;
    }

}
