package rest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository  extends  MongoRepository<Product, String>{
    List<Product> findBypid(@Param("pid") int pid);
    List<Product> findByCurrencyCode(@Param("currencyCode") String currencyCode);
    List<Product> findByName(@Param("name") String name);
    List<Product> findByValue(@Param("value") String value);

}
