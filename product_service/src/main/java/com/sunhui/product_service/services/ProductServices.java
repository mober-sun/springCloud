package com.sunhui.product_service.services;

import com.sunhui.product_service.domain.Product;

import java.util.List;

public interface ProductServices {

    List<Product> listProduct();

    Product findById(int id);
}
