package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplicationClass {

    @Autowired
    private ProductRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(MainApplicationClass.class, args);
    }

}
