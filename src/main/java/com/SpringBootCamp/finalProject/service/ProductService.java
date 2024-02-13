package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.repository.IProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private IProductRepository productRepo;

    public ProductService(IProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product saveProduct(Product product) {

       return productRepo.save(product);
    }

    @Override
    public List<Product> productList() {
        return productRepo.findAll();
    }

    @Override
    public Product findProduct(Long productCode) {
        return productRepo.findById(productCode).orElseThrow(() -> new EntityNotFoundException("Product with id " + productCode + " not found"));
    }

    @Override
    public void deleteProduct(Long productCode) {
        productRepo.deleteById(productCode);
    }

    @Override
    public Product editProduct(Long productCode,@Valid Product updatedProduct) {
        Product product = productRepo.findById(productCode).orElseThrow(() -> new EntityNotFoundException("Product with id " + productCode + " not found"));
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updatedProduct, product);
        return this.saveProduct(product);
    }

    @Override
    public List<Product> lowerStock(Double quantity) {
        return productRepo.findAllProductsByAvailableQuantityLessThan(quantity);
    }

}
