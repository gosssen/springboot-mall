package com.gosssen.springbootmall.dao;

import com.gosssen.springbootmall.dto.ProductQueryParams;
import com.gosssen.springbootmall.dto.ProductRequest;
import com.gosssen.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Integer countProduct(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
