package com.myRetail.web.controller;


import com.myRetail.persistence.model.Product;
import com.myRetail.service.impl.ProductServiceImpl;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;


//controller for the REST interface, CrossOrigin only for testing.
@CrossOrigin
@RestController
public class ProductController {

    private ProductServiceImpl projectService;

    //default product page will load all the products in the db
    @RequestMapping("/products")
    public Collection<Product> product() {
        return projectService.findAll();
    }

    //will seed the mongo db with 2 products and then return all the products in mongo
    @RequestMapping("/seed")
    public Collection<Product> productSeed() {
        projectService.save(new Product(1234L, "Test Movie", 100.25));
        projectService.save(new Product(1235L, "Test Movie 2: Now with Ducks", 11.43));

        return projectService.findAll();
    }

    @DeleteMapping("/clean")
    public void cleanUp() {
        projectService.deleteAll();
    }

    //fetches only the matching product
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id) {
        Optional<Product> match = projectService.findById(id);
        return match.orElseGet(() -> fetchFromRedSky(id));
    }

    //will update a product in mongo responds with the updated product
    @PutMapping("/product/{id}")
    public void putProduct(@PathVariable Long id, @RequestBody Product product) {
        //todo
    }


    //todo post mapping
    //will put a new product into mongo, response with the new product
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void postProduct(@RequestBody Product product) {
        Optional<Product> match =  projectService.findById(product.getId()); //todo fix

        //todo add logic to add product or return already exists
    }

    //removes a product from mongo
    @DeleteMapping("/product/{id}")
    public HttpStatus deleteProduct(@PathVariable Long id) {
        projectService.delete(id);
        return HttpStatus.OK;
    }


    //todo
    private Product fetchFromRedSky(Long id){
        return null;
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
                projectService.save(product);
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
