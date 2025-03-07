package com.sample.product_service.Controller;

import com.sample.product_service.Entity.Product;
import com.sample.product_service.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAll(){
        return productService.getAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Product> create(@RequestBody Product product){
            return ResponseEntity.ok(productService.create(product));
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<Product> updateById(@PathVariable Long productId,
                                              @RequestBody Product product){
        return productService.updateById(productId,product);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeById(@PathVariable Long productId){
            return ResponseEntity.ok(productService.remove(productId));
    }
}
