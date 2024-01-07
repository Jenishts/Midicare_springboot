package com.medicare.controller;

import com.medicare.entity.Product;
import com.medicare.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductControler {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> list(){
        return ResponseEntity.ok(productService.listProduct());
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product){
        System.out.println(product.getSeller());
            return ResponseEntity.ok(productService.save(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        productService.delete(id);
        return ResponseEntity.ok("Deleted...");
    }
}
