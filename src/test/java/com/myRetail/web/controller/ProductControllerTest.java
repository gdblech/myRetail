package com.myRetail.web.controller;

import com.myRetail.persistence.model.Product;
import com.myRetail.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductControllerTest {

    private static final String BASE_URL = "http://localhost:8080/";
    private static final long REDSKY_ID = 13860428L;

    private RestTemplate restTemplate = new RestTemplate();

    ProductServiceImpl productService;

    Product p1 = new Product(1234L, "Test Movie", 100.25);
    Product p2 = new Product(1235L, "Test Movie 2: Now with Ducks", 11.43);
    Product p3 = new Product(1236L, "Captain America", 12.0);

    //test to see if the seed responded correctly, and that the DB contains the seeded projects
    @Test
    void productSeedTest() {
        ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(BASE_URL +"seed", Product[].class);

        assertNotNull(responseEntity.getBody());

        List<Product> response = Arrays.asList(responseEntity.getBody());
        assertThat(response,hasItems(p1, p2));

        Collection<Product> dbList = productService.findAll();
        assertThat(dbList,hasItems(p1, p2));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getProduct_thatExists() {
        ResponseEntity<Product> responseEntity = restTemplate.getForEntity(BASE_URL + p1.getId(), Product.class);

        assertEquals(p1, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getProduct_thatExistsInRedSky() {
        ResponseEntity<Product> responseEntity = restTemplate.getForEntity(BASE_URL + REDSKY_ID, Product.class);
        Optional<Product> dbResponse = productService.findById(REDSKY_ID);

        assertTrue(dbResponse.isPresent());

        Product redSkyProduct = dbResponse.get();
        assertEquals(redSkyProduct,responseEntity.getBody());
        assertEquals(redSkyProduct.getId(), REDSKY_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getProduct_thatDoesNotExist() {
        ResponseEntity<Product> responseEntity = restTemplate.getForEntity(BASE_URL + "0",Product.class);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void postProduct() {
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(BASE_URL + "product",p3, Product.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Optional<Product> dbResponse = productService.findById(p3.getId());
        assertTrue(dbResponse.isPresent());
        assertEquals(p3, dbResponse.get());
    }

    @Test
    void putProduct() {
        Product p4 = new Product(p1);
        p4.setName("Replace Name");
        restTemplate.put(BASE_URL + p1.getId(), p4);
        Optional<Product> dbResponse = productService.findById(p4.getId());
        assertTrue(dbResponse.isPresent());
        assertEquals(p4, dbResponse.get());
        assertNotEquals(p1, dbResponse.get());
    }

    @Test
    void deleteProduct_thatExists() {
        restTemplate.delete(BASE_URL + p3.getId());

        Optional<Product> dbResponse = productService.findById(p3.getId());
        assertFalse(dbResponse.isPresent());

    }

    @Test
    void cleanTest(){
        restTemplate.delete(BASE_URL + "clean");

        Collection<Product> dbCheck = productService.findAll();
        assertThat(dbCheck, hasSize(0));
    }

}