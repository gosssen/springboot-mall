package com.gosssen.springbootmall.dao.impl;

import com.gosssen.springbootmall.dao.ProductDao;
import com.gosssen.springbootmall.dto.ProductQueryParams;
import com.gosssen.springbootmall.dto.ProductRequest;
import com.gosssen.springbootmall.model.Product;
import com.gosssen.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        StringBuilder sql = new StringBuilder()
                .append("SELECT ")
                .append("product_id, product_name, category, image_url, price, ")
                .append("stock, description, created_date, last_modified_date ")
                .append("FROM product WHERE 1 = 1 ");

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        addFilteringSql(sql, map, productQueryParams);

        // 排序
        sql.append("ORDER BY ").append(productQueryParams.getOrderBy()).append(" ")
                .append(productQueryParams.getSort()).append(" ");

        // 分頁
        sql.append("LIMIT :limit OFFSET :offset ");
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql.toString(), map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        StringBuilder sql = new StringBuilder()
                .append("SELECT COUNT(1) FROM product WHERE 1 = 1 ");

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        addFilteringSql(sql, map, productQueryParams);

        return namedParameterJdbcTemplate.queryForObject(sql.toString(), map, Integer.class);
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT " +
                "product_id, product_name, category, image_url, price, " +
                "stock, description, created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList.size() > 0 ? productList.get(0) : null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (" +
                "product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, " +
                ":description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET " +
                "product_name = :productName, category = :category, image_url = :imageUrl, price = :price, " +
                "stock = :stock, description = :description , last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());

        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    private StringBuilder addFilteringSql(StringBuilder sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
        if (productQueryParams.getCategory() != null) {
            sql.append("AND category = :category ");
            map.put("category", productQueryParams.getCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            sql.append("AND product_name LIKE :search ");
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        return sql;
    }
}
