package rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @RequestMapping("/product")
    public String product(){
        return "You Made it to the Product page";
    }

//    @RequestMapping("/")
//    public String index(){
//        return "You Made it to the main page";
//    }
//

}
