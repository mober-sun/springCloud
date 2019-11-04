package com.sunhui.order_service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunhui.order_service.domian.ProductOrder;
import com.sunhui.order_service.service.ProductClient;
import com.sunhui.order_service.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductOrderServiceImpl  implements ProductOrderService {

    // 方式1、无feign方式，获取restTemplate rest请求服务地址 获取数据
    @Autowired
    private RestTemplate restTemplate;

    //方式2: feign 模拟 接口的方式；
    @Autowired
    private ProductClient productClient;



    @Override
    public ProductOrder save(int userId, int productId) throws IOException {

        // 方式1、无feign 原始 rest 调用其他服务；
        /**
         * 1、注意，只有注解了@bean 和 @LoadBalanced   ribbon 负载均衡的方式 才可以直接使用 http:// 注册服务名称/xx/xx?xx
         *    否则 只能用原始的：http//ip地址：端口/xx/xx?xx 方式；
         */
        //Map<String,Object> productMap  = restTemplate.getForObject("http://product-service/api/v1/product/find?id="+productId, Map.class);
        //
        //ProductOrder productOrder1 = new ProductOrder();
        //productOrder1.setCreateTime(new Date());
        //productOrder1.setUserId(userId);
        //productOrder1.setTradeNo(UUID.randomUUID().toString());
        //productOrder1.setProductName(productMap.get("name").toString());
        //productOrder1.setPrice(Integer.parseInt(productMap.get("price").toString()));
        //return productOrder1;
        // 方式2，feign 模拟接口方式，获取注册到eureke上服务数据
        String response = productClient.findById(productId);
        JsonNode jsonNode = new ObjectMapper().readTree(response);  // json 数据转成 jsonNode

        ProductOrder productOrder2 = new ProductOrder();
        productOrder2.setCreateTime(new Date());
        productOrder2.setUserId(userId);
        productOrder2.setTradeNo(UUID.randomUUID().toString());
        productOrder2.setProductName(jsonNode.get("name").toString());
        productOrder2.setPrice(Integer.parseInt(jsonNode.get("price").toString()));

        return productOrder2;
    }
}
