package com.SpringBootCamp.finalProject.repository;

import com.SpringBootCamp.finalProject.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class IProductRepositoryTest {

    @Autowired
    private IProductRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void Finding_products_with_available_quantity_less_than_11_returns_both_products() {
        //given
        Product product = new Product(1L,"Cellphone","Iphone",159.99,10.0,new ArrayList<>());
        Product product2 = new Product(2L,"Mousepad","HyperX",45.99,5.0,new ArrayList<>());
        underTest.save(product);
        underTest.save(product2);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);
        //when
        List<Product> expected = underTest.findAllByAvailable_quantityLessThan(11.0);
        //then
        assertThat(expected).usingRecursiveComparison().isEqualTo(productList);
    }
    @Test
    void Finding_products_with_available_quantity_less_than_6_returns_one_product() {
        //given
        Product product = new Product(1L,"Cellphone","Iphone",159.99,10.0,new ArrayList<>());
        Product product2 = new Product(2L,"Mousepad","HyperX",45.99,5.0,new ArrayList<>());
        underTest.save(product);
        underTest.save(product2);
        List<Product> productList = new ArrayList<>();
        productList.add(product2);
        //when
        List<Product> expected = underTest.findAllByAvailable_quantityLessThan(6.0);
        //then
        assertThat(expected).usingRecursiveComparison().isEqualTo(productList);
    }
    @Test
    void Finding_products_with_available_quantity_less_than_5_returns_empty_list() {
        //given
        Product product = new Product(1L,"Cellphone","Iphone",159.99,10.0,new ArrayList<>());
        Product product2 = new Product(2L,"Mousepad","HyperX",45.99,5.0,new ArrayList<>());
        underTest.save(product);
        underTest.save(product2);
        //when
        List<Product> expected = underTest.findAllByAvailable_quantityLessThan(5.0);
        //then
        assertThat(expected.size()).isEqualTo(0);
    }
}