package ec.com.edimca.posbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ec.com.edimca.posbackend.model.OrderHeader;
import ec.com.edimca.posbackend.model.Product;
import ec.com.edimca.posbackend.service.OrderService;
import ec.com.edimca.posbackend.service.ProductService;

@Controller
@RequestMapping("app")
public class AppController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/health")
    public ResponseEntity<String> health() {

        return new ResponseEntity<String>("Backend running on port 8081", HttpStatus.OK);
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> getAll() {

        List<Product> result = this.productService.getAllProducts();
        return new ResponseEntity<List<Product>>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<Boolean> newProduct(@RequestBody OrderHeader order) {

        Boolean result = this.orderService.newOrder(order);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
    
}
