package com.SpringBootCamp.finalProject.controller;

import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productServ;

    @GetMapping("/product/list")
    public List<Product> getProducts(){
        return productServ.productList();
    }

    @GetMapping("/product/list/{productCode}")
    public Product getProducts(@PathVariable("productCode") Long productCode){
        return productServ.findProduct(productCode);
    }

    @PostMapping("/product/create")
    public void createProduct(@RequestBody Product product){
        productServ.saveProduct(product);
    }

    @DeleteMapping("/product/delete/{productCode}")
    public void deleteProduct(@PathVariable Long productCode){
        productServ.deleteProduct(productCode);
    }

    @PutMapping("/product/edit/{productCode}")
    public void editProduct(@PathVariable Long productCode,
                              @RequestBody Product product){
        productServ.editProduct(productCode,product);
    }

}
