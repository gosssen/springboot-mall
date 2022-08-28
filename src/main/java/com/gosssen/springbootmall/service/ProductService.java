package com.gosssen.springbootmall.service;

import com.gosssen.springbootmall.dto.ProductRequest;
import com.gosssen.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
