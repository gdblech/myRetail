package com.myRetail.web.controller;


import com.myRetail.persistence.model.Product;
import com.myRetail.persistence.model.RedSkyProduct;
import com.myRetail.service.impl.ProductServiceImpl;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;


//controller for the REST interface.
@RestController
public class ProductController {


    private ProductServiceImpl productService;

    public ProductController(ProductServiceImpl projectService) {
        this.productService = projectService;
    }

    //default product page will load all the products in the db
    @GetMapping("/products")
    public Collection<Product> product() {
        return productService.findAll();
    }

    //will seed the mongo db with 2 products and then return all the products in mongo
    @GetMapping("/seed")
    public Collection<Product> productSeed() {
        productService.save(new Product(1234L, "Test Movie", 100.25));
        productService.save(new Product(1235L, "Test Movie 2: Now with Ducks", 11.43));

        return productService.findAll();
    }

    @DeleteMapping("/clean")
    public void cleanUp() {
        productService.deleteAll();
    }

    //fetches only the matching product
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id) {
        Optional<Product> match = productService.findById(id);
        return match.orElseGet(() -> fetchFromRedSky(id));
    }

    //will update a product in mongo responds with the updated product
    @PutMapping("/product/{id}")
    public void putProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> found = productService.findById(product.getId());
        if (!found.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        productService.update(product);
    }


    //will put a new product into mongo, response with the new product
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product postProduct(@RequestBody Product product) {
        Optional<Product> match = productService.findById(product.getId());
        if (match.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Resource already exists.");
        }
        productService.save(product);
        return product;

    }

    //removes a product from mongo
    @DeleteMapping("/product/{id}")
    public HttpStatus deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return HttpStatus.OK;
    }


    //todo
    private Product fetchFromRedSky(Long id) {
        String RedSkyFirstHalf = "https://redsky.target.com/v3/pdp/tcin/";
        String RedSkySecondHalf = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate#_blank";
        System.out.println(RedSkyFirstHalf + id + RedSkySecondHalf);
        ResponseEntity<RedSkyProduct> responseEntity = new RestTemplate().getForEntity(RedSkyFirstHalf + id + RedSkySecondHalf,RedSkyProduct.class);
        if (!responseEntity.hasBody()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found.");
        }
        Product response = responseEntity.getBody().getProduct();
        if ( response == null || response.getId() == null || response.getId() == 0 || response.getName() == null || response.getName().equals("")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found.");
        }
        productService.save(response);
        return response;
    }

    @Deprecated
    //fetches product from remote rest server
    private String fetchFromRemote(Long pid) {

        try {
            URL url = new URL("https://redsky.target.com/v2/pdp/tcin/" + pid +
                    "?excludes=bulk_ship,promotion,rating_and_review_reviews,question_answer_statistics,deep_red_labels," +
                    "available_to_promise_network,rating_and_review_statistics");
            HttpURLConnection redSky = (HttpURLConnection) url.openConnection();
            redSky.setRequestMethod("GET");
            redSky.connect();
            if (redSky.getResponseCode() == 200) {
                BufferedReader reply = new BufferedReader(new InputStreamReader(redSky.getInputStream()));
                String response = reply.readLine();

                reply.close();
                redSky.disconnect();

                JSONObject json = new JSONObject(response);
                BigDecimal value = json.getJSONObject("product").getJSONObject("price").getJSONObject("listPrice").getBigDecimal("price");
                String name = json.getJSONObject("product").getJSONObject("item").getJSONObject("product_description").getString("title");
                //int pid, String name, String currencyCode, BigDecimal value
                Product product = new Product(pid, name, value);
                productService.save(product);
                return product.toString();
            }
        } catch (IOException e) {
            return e.toString();
        }

        return "error failed to fetch";
    }

    @RequestMapping("/")
    public String index() {
        return "You Made it to the root page, but there is nothing here, sorry.";
    }


}
