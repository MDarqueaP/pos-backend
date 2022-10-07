package ec.com.edimca.posbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ec.com.edimca.posbackend.model.Product;
import ec.com.edimca.posbackend.service.ProductService;
import ec.com.edimca.posbackend.validation.ProductValidation;

@Controller
public class ProductAdminController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> getAll() {

        List<Product> result = this.productService.getAllProducts();
        return new ResponseEntity<List<Product>>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<Boolean> newProduct(
            @Validated(value = ProductValidation.New.class) @RequestBody Product product) {

        Boolean result = this.productService.newProduct(product);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") int id) {

        Product result = this.productService.getProduct(id);
        return new ResponseEntity<Product>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<Boolean> updateProduct(@PathVariable("id") int id,
            @Validated(value = ProductValidation.New.class) @RequestBody Product product) {

        Boolean result = this.productService.updateProduct(id, product);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") int id) {

        Boolean result = this.productService.deleteProduct(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

}
