package com.tedev.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tedev.crud.model.Product;
import com.tedev.crud.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<Object> products() {
        return this.productService.getProducts();
    }

    @PostMapping()
    public ResponseEntity<Object> newProduct(@RequestBody Product product) {
        return this.productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable("id") Long id) {
        return this.productService.upateProduct(product, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeProduct(@PathVariable("id") Long id) {
        return this.productService.deleteProduct(id);
    }
}
