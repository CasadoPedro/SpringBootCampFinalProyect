package com.SpringBootCamp.finalProject.repository;

import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.model.Sale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ISaleRepositoryTest {

    @Autowired
    private ISaleRepository underTest;
    @Autowired
    private IClientRepository clientRepo;
    @Autowired
    private IProductRepository productRepo;

    private void setupTestData() {
        Client standarClient = new Client(1L, "Max", "Terry", "maxterry@gmail.com", new ArrayList<>());
        clientRepo.save(standarClient);
        Product product = new Product(1L, "Cellphone", "Iphone", 159.99, 10.0, new ArrayList<>());
        productRepo.save(product);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Sale sale1 = new Sale(1L, LocalDate.parse("2023-06-07"), 10.99, standarClient, productList);
        Sale sale2 = new Sale(2L, LocalDate.parse("2023-08-15"), 100.99, standarClient, productList);
        Sale sale3 = new Sale(3L, LocalDate.parse("2023-06-07"), 1000.99, standarClient, productList);

        underTest.save(sale1);
        underTest.save(sale2);
        underTest.save(sale3);
    }

    @Test
    void searching_2023_06_07_returns_list_with_two_sales() {
        // given
        setupTestData();

        // when
        List<Sale> expected = underTest.findAllByDate(LocalDate.parse("2023-06-07"));

        // then
        List<Sale> saleList = Arrays.asList(
                new Sale(1L, LocalDate.parse("2023-06-07"), 10.99,
                        new Client(1L, "Max", "Terry", "maxterry@gmail.com", new ArrayList<>()),
                        Collections.singletonList(new Product(1L, "Cellphone", "Iphone", 159.99, 10.0, new ArrayList<>()))),
                new Sale(3L, LocalDate.parse("2023-06-07"), 1000.99,
                        new Client(1L, "Max", "Terry", "maxterry@gmail.com", new ArrayList<>()),
                        Collections.singletonList(new Product(1L, "Cellphone", "Iphone", 159.99, 10.0, new ArrayList<>())))
        );

        assertThat(expected).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(saleList);
    }

    @Test
    void searching_2023_08_15_returns_list_with_one_sale() {
        // given
        setupTestData();

        // when
        List<Sale> expected = underTest.findAllByDate(LocalDate.parse("2023-08-15"));

        // then
        List<Sale> saleList = Collections.singletonList(
                new Sale(2L, LocalDate.parse("2023-08-15"), 100.99,
                        new Client(1L, "Max", "Terry", "maxterry@gmail.com", new ArrayList<>()),
                        Collections.singletonList(new Product(1L, "Cellphone", "Iphone", 159.99, 10.0, new ArrayList<>())))
        );

        assertThat(expected).usingRecursiveComparison().isEqualTo(saleList);
    }
    @Test
    void searching_2023_08_16_returns_empty_list() {
        // given
        setupTestData();

        // when
        List<Sale> expected = underTest.findAllByDate(LocalDate.parse("2023-08-16"));

        // then
        assertThat(expected.size()).isEqualTo(0);
    }
    @Test
    void top_sale_found_equals_actual_top_sale() {
        // given
        setupTestData();
        //when
        Sale topSale = underTest.findTopSale();
        //then
        assertThat(topSale).usingRecursiveComparison().isEqualTo(new Sale(3L, LocalDate.parse("2023-06-07"), 1000.99,
                new Client(1L, "Max", "Terry", "maxterry@gmail.com", new ArrayList<>()),
                Collections.singletonList(new Product(1L, "Cellphone", "Iphone", 159.99, 10.0, new ArrayList<>())))
        );
    }
}