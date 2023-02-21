package com.controller;

import com.entity.Product;
import com.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        final Product saveProduct = productRepository.save(product);
        final URI productURI = URI.create("/products/" + saveProduct.getId());
        return ResponseEntity.created(productURI).body(saveProduct);
    }

    @GetMapping("/list")
    public List<Product> list() {
        return productRepository.findAll();
    }
}
