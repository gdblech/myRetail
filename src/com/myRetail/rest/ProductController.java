package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

//controller for the REST interface, CrossOrigin only for testing.
@CrossOrigin
@RestController
public class ProductController {

    //mongo connection
    @Autowired
    private ProductRepository repository;

    //default product page will load all the products in the mongodb
    @RequestMapping("/product")
    public String product() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(repository.findAll());
        }catch(JsonProcessingException e){
            return new Product().toString();
        }
    }

    //will seed the mongo db with 1 product and then return all the products in mongo
    @RequestMapping("/seed")
    public String productSeed() {
        repository.insert(new Product("123", "Test Movie", "USD", 100.25));
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(repository.findAll());
        }catch(JsonProcessingException e){
            return "failed to parse";
        }
    }

    //fetches only the matching product
    @GetMapping("/product/{pid}")
    public String getProduct(@PathVariable String pid) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Product> matches = repository.findBypid(pid);
            if (matches.size() == 0) {
                return fetchFromRemote(pid);
            }
            for (Product p : matches) {
                if (p.getPid().equals(pid)) {
                    return mapper.writeValueAsString(p);
                }
            }
        }catch(JsonProcessingException e){
            return "Failed to parse json";
        }
        return "not available";
    }

    //will update a product in mongo responds with the updated product
    @PutMapping("/product/{pid}")
    public String postProduct(@PathVariable String pid, @RequestBody Product product) {
        repository.deleteAll(repository.findBypid(pid));
        repository.insert(product);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(product);
        }catch(JsonProcessingException e){
            return "failed to parse";
        }
    }

    //will put a new product into mongo, respons with the new product
    @PutMapping("/product")
    public String putProduct(@RequestBody Product product) {
        List<Product> available =  repository.findBypid(product.getPid());
        if(available.size() == 0){
            repository.insert(product);
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(product);
            }catch(JsonProcessingException e){
                return "failed to parse";
            }
        }else {
            return "Product already exists";
        }
    }


    //removes a product from mongo
    @DeleteMapping("/product/{pid}")
    public String deleteProduct(@PathVariable String pid) {
        repository.deleteAll(repository.findBypid(pid));
        return "success!";
    }

    //fetches product from remote rest server
    private String fetchFromRemote(String pid) {

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
                Product product = new Product(pid, name, CurrentPrice.USD, value);
                repository.insert(product);
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
