package com.sunhui.order_service.service;

import com.google.common.collect.Maps;
import com.sunhui.order_service.controller.ProductOrderController;
import com.sunhui.order_service.domian.ProductOrder;

import java.io.IOException;
import java.util.HashMap;

public interface ProductOrderService {

    /**
     * 下单接口
     * @param userId
     * @param productId
     * @return
     */
    ProductOrder save(int userId, int productId) throws IOException;


}
