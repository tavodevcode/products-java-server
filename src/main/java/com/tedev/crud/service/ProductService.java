package com.tedev.crud.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tedev.crud.model.Product;
import com.tedev.crud.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private static final String DATA = "data";
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Object> getProducts() {
        // { vaue: key }
        HashMap<String, Object> res = new HashMap<>();

        res.put(DATA, this.productRepository.findAll());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public Product getProduct(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    public ResponseEntity<Object> addProduct(Product product) {
        Optional<Product> productExists = this.productRepository.findProductByName(product.getName());
        HashMap<String, Object> res = new HashMap<>();

        if (productExists.isPresent()) {
            res.put(ERROR, true);
            res.put(MESSAGE, "Product already exists");

            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        res.put(DATA, this.productRepository.save(product));

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> upateProduct(Product product, Long id) {
        Optional<Product> productExists = this.productRepository.findById(id);
        HashMap<String, Object> res = new HashMap<>();

        if (productExists.isEmpty()) {
            res.put(ERROR, true);
            res.put(MESSAGE, "Product does not exists");

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        Optional<Product> productByName = this.productRepository.findProductByName(product.getName());
        if (productByName.isPresent() && !productByName.get().getId().equals(id)) {
            res.put(ERROR, true);
            res.put(MESSAGE, "Product with this name already exists");

            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

        Product existingProduct = productExists.get();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCreatedAt(product.getCreatedAt());

        res.put(DATA, this.productRepository.save(existingProduct));

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteProduct(Long id) {
        Optional<Product> productExists = this.productRepository.findById(id);
        HashMap<String, Object> res = new HashMap<>();

        if (productExists.isEmpty()) {
            res.put(ERROR, true);
            res.put(MESSAGE, "Product does not exists");

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        this.productRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
