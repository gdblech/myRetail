package rest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//extends mongo searches for product specifics
public interface ProductRepository  extends  MongoRepository<Product, String>{
    List<Product> findBypid(@Param("pid") int pid);
    List<Product> findByName(@Param("name") String name);
}
