package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Product;

import java.util.List;

public interface IProductService {

    public Product saveProduct(Product product);

    public List<Product> productList();

    public Product findProduct(Long productCode);

    public void deleteProduct(Long productCode);

    public Product editProduct(Long productCode,Product updatedProduct);

    public List<Product> lowerStock(Double quantity);
}
