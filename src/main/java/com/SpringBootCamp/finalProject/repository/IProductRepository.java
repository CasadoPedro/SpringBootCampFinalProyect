package com.SpringBootCamp.finalProject.repository;

import com.SpringBootCamp.finalProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository <Product,Long>{
}
