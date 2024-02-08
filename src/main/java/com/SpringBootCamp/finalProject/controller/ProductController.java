package com.SpringBootCamp.finalProject.controller;

import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productServ;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productServ.productList());
    }

    @GetMapping("/list/{productCode}")
    public ResponseEntity<Product> getProduct(@PathVariable("productCode") Long productCode){
        return ResponseEntity.ok(productServ.findProduct(productCode));
    }
    @GetMapping("/lower_stock/{quantity}")
    public ResponseEntity<List<Product>> lowerStock(@PathVariable Double quantity){
        return ResponseEntity.ok(productServ.lowerStock(quantity));
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product){
        return new ResponseEntity<>(productServ.saveProduct(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productCode}")
    public ResponseEntity deleteProduct(@PathVariable Long productCode){
        productServ.deleteProduct(productCode);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{productCode}")
    public ResponseEntity<Product> editProduct(@PathVariable Long productCode,
                                               @RequestBody Product product){
        return new ResponseEntity<>(productServ.editProduct(productCode,product), HttpStatus.OK);
    }

}
