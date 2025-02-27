package com.sample.product_service.Service;


import com.sample.product_service.Entity.Product;
import com.sample.product_service.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product create(Product product){
        return productRepository.save(product);
    }

    public String remove(Long productId){
        try {
            productRepository.deleteById(productId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return "ProductId Removed SuccessFully..!!";
    }

    public ResponseEntity<Product> updateById(Long productId, Product product){
        Optional<Product> updateProduct= productRepository.findById(productId);
        if (updateProduct.isPresent()){
            Product existingProduct=updateProduct.get();
            if (product.getProductName()!=null){
                existingProduct.setProductName(product.getProductName());
            }
            if (product.getPrice()>0){
                existingProduct.setPrice(product.getPrice());
            }
            if (product.getCategory()!=null){
                existingProduct.setCategory(product.getCategory());
            }
            return ResponseEntity.ok(productRepository.save(existingProduct));
        }
       return ResponseEntity.notFound().build();
    }
}
