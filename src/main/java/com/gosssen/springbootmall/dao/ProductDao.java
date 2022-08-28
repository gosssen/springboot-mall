package com.gosssen.springbootmall.dao;

import com.gosssen.springbootmall.dto.ProductRequest;
import com.gosssen.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
