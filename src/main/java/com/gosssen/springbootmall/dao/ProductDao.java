package com.gosssen.springbootmall.dao;

import com.gosssen.springbootmall.constant.ProductCategory;
import com.gosssen.springbootmall.dto.ProductRequest;
import com.gosssen.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
