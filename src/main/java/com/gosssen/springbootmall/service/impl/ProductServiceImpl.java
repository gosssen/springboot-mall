package com.gosssen.springbootmall.service.impl;

import com.gosssen.springbootmall.dao.ProductDao;
import com.gosssen.springbootmall.model.Product;
import com.gosssen.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
