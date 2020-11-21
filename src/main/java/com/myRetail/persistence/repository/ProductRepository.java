package com.myRetail.persistence.repository;

import com.myRetail.persistence.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//extends mongo searches for product specifics

public interface ProductRepository extends MongoRepository<Product, Long> {
    Iterable<Product> findByName(@Param("name") String name);
}
