package com.myRetail.service.impl;

import com.myRetail.persistence.model.Product;
import com.myRetail.persistence.repository.ProductRepository;
import com.myRetail.service.ProductServiceInterface;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductServiceInterface {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Collection<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Iterable<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }
}
