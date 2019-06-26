package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @RequestMapping("/product")
    public String product(){
        repository.insert(new Product());
        return repository.findAll().toString();
    }

    @GetMapping("/product/{pid}")
    public String getProduct(@PathVariable int pid){
        List<Product> matches = repository.findBypid(pid);
        if(matches.size() == 0){
            return fetchFromRemote(pid);
        }
        for(Product p: matches){
            if(p.getPid() == pid){
                return p.toString();
            }
        }
        return "failed to get";
    }

    @PostMapping("/product/{pid}")
    public String postProduct(@PathVariable String pid, @RequestBody Map<String, String> body){
        return null;
    }

    @PutMapping("/product/{pid}")
    public String putProduct(@PathVariable String pid, @RequestBody Map<String, String> body){
        return null;
    }

    @DeleteMapping("/product/{pid}")
    public String deleteProduct(@PathVariable String pid){
        return null;
    }

    private String fetchFromRemote(int pid) {
        return "not in mongo";
    }
//    @RequestMapping("/")
//    public String index(){
//        return "You Made it to the main page";
//    }
//

}
