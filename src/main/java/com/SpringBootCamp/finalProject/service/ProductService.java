package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.repository.IProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    ModelMapper modelMapper = new ModelMapper();

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
        Product product = productRepo.findById(productCode).orElseThrow(() -> new EntityNotFoundException("Product with id " + productCode + " not found"));
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updatedProduct, product);
        this.saveProduct(product);
    }

    @Override
    public List<Product> lowerStock(Double quantity) {
        return productRepo.findAllByAvailable_quantityLessThan(quantity);
    }

}
