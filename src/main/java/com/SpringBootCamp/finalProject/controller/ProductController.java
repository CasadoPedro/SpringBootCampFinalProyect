package com.SpringBootCamp.finalProject.controller;

import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productServ;

    @GetMapping("/list")
    public List<Product> getProducts(){
        return productServ.productList();
    }

    @GetMapping("/list/{productCode}")
    public Product getProducts(@PathVariable("productCode") Long productCode){
        return productServ.findProduct(productCode);
    }
    @GetMapping("/lower_stock/{quantity}")
    public List<Product> lowerStock(@PathVariable Double quantity){
        return productServ.lowerStock(quantity);
    }

    @PostMapping("/create")
    public void createProduct(@RequestBody Product product){
        productServ.saveProduct(product);
    }

    @DeleteMapping("/delete/{productCode}")
    public void deleteProduct(@PathVariable Long productCode){
        productServ.deleteProduct(productCode);
    }

    @PutMapping("/edit/{productCode}")
    public void editProduct(@PathVariable Long productCode,
                              @RequestBody Product product){
        productServ.editProduct(productCode,product);
    }

}
