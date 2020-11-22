package com.myRetail.web.controller;

import com.myRetail.persistence.model.Product;
import com.myRetail.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    private static final String BASE_URL = "http://localhost:8080/";
    private static final long REDSKY_ID = 13860428L;
    @Autowired
    ProductServiceImpl productService;
    Product p1 = new Product(1234L, "Test Movie", 100.25);
    Product p2 = new Product(1235L, "Test Movie 2: Now with Ducks", 11.43);
    Product p3 = new Product(1236L, "Captain America", 12.0);
    private RestTemplate restTemplate = new RestTemplate();

    //test to see if the seed responded correctly, and that the DB contains the seeded projects
    @Test
    @Order(1)
    void productSeedTest() {
        ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(BASE_URL + "seed", Product[].class);

        assertNotNull(responseEntity.getBody());

        List<Product> response = Arrays.asList(responseEntity.getBody());
        assertThat(response,hasItems(p1, p2));

        Collection<Product> dbList = productService.findAll();
        assertThat(dbList, hasItems(p1, p2));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(2)
    void getProduct_thatExists() {
        ResponseEntity<Product> responseEntity = restTemplate.getForEntity(BASE_URL + "product/" + p1.getId(), Product.class);

        assertEquals(p1, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(3)
    void getProduct_thatExistsInRedSky() {
        ResponseEntity<Product> responseEntity = restTemplate.getForEntity(BASE_URL + "product/" + REDSKY_ID, Product.class);
        Optional<Product> dbResponse = productService.findById(REDSKY_ID);

        assertTrue(dbResponse.isPresent());

        Product redSkyProduct = dbResponse.get();
        assertEquals(redSkyProduct, responseEntity.getBody());
        assertEquals(redSkyProduct.getId(), REDSKY_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(5)
    void postProduct() {
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(BASE_URL + "product", p3, Product.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Optional<Product> dbResponse = productService.findById(p3.getId());
        assertTrue(dbResponse.isPresent());
        assertEquals(p3, dbResponse.get());
    }

    @Test
    @Order(6)
    void putProduct() {
        Product p4 = new Product(p1);
        p4.setName("Replace Name");
        restTemplate.put(BASE_URL + "product/" + p1.getId(), p4);
        Optional<Product> dbResponse = productService.findById(p4.getId());
        assertTrue(dbResponse.isPresent());
        assertEquals(p4, dbResponse.get());
        assertNotEquals(p1, dbResponse.get());
    }

    @Test
    @Order(7)
    void deleteProduct_thatExists() {
        restTemplate.delete(BASE_URL + "product/" + p3.getId());

        Optional<Product> dbResponse = productService.findById(p3.getId());
        assertFalse(dbResponse.isPresent());

    }

    @Test
    @Order(8)
    void cleanTest() {
        restTemplate.delete(BASE_URL + "clean");

        Collection<Product> dbCheck = productService.findAll();
        assertThat(dbCheck, hasSize(0));
    }

}