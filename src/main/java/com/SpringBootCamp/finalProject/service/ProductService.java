package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepo;

    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public List<Product> productList() {
        return productRepo.findAll();
    }

    @Override
    public Product findProduct(Long productCode) {
        return productRepo.findById(productCode).orElse(null);
    }

    @Override
    public void deleteProduct(Long productCode) {
        productRepo.deleteById(productCode);
    }

    @Override
    public void editProduct(Long productCode, Product updatedProduct) {
        Product product = this.findProduct(productCode);
        product.setName(updatedProduct.getName());
        product.setBrand(updatedProduct.getBrand());
        product.setCost(updatedProduct.getCost());
        product.setAvailable_quantity(updatedProduct.getAvailable_quantity());
        this.saveProduct(product);
    }
}
