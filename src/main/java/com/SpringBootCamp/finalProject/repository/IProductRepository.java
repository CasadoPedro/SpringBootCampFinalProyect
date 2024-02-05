package com.SpringBootCamp.finalProject.repository;

import com.SpringBootCamp.finalProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository <Product,Long>{

    @Query("SELECT p FROM Product p WHERE p.available_quantity < :quantity")
    List<Product> findAllByAvailable_quantityLessThan(@Param("quantity") Double quantity);
}
