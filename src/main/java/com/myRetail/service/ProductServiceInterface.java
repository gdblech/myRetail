package com.myRetail.service;

import com.myRetail.persistence.model.Product;

import java.util.Collection;
import java.util.Optional;

public interface ProductServiceInterface {
    Optional<Product> findById(Long id);
    Product save(Product product);
    Collection<Product> findAll();
    void delete(Long id);
    Iterable<Product> findByName(String name);
    void deleteAll();
    Product update(Product product);
}
