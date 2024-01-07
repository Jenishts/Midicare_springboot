package com.medicare.services;


import com.medicare.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> listProduct();

    Product save(Product product);

    void delete(Long id);
}
