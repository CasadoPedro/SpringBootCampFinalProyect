package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.repository.IProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private IProductRepository productRepo;
    private ProductService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProductService(productRepo);
    }

    @Test
    void product_list_method_calls_product_repository_find_all() {
        //when
        underTest.productList();
        //then
        verify(productRepo).findAll();
    }

    @Test
    void save_product_calls_repository_save_with_valid_product() {
        //given
        Product product = new Product(1L,"Phone","IPhone",99.99,100.0,new ArrayList<>());
        //when
        underTest.saveProduct(product);
        //then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepo).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();
        assertThat(capturedProduct).isEqualTo(product);
    }

    @Test
    void find_product_with_invalid_code_throws_EntityNotFoundException() {
        //given
        given(productRepo.findById(any())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(()-> underTest.findProduct(anyLong()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("not found");
        verify(productRepo).findById(anyLong());
    }

    @Test
    void delete_product_calls_repository_delete_by_id_with_valid_product_code() {
        //given
        Long productCode = 999L;
        //when
        underTest.deleteProduct(productCode);
        //then
        ArgumentCaptor<Long> productCodeArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(productRepo).deleteById(productCodeArgumentCaptor.capture());
        Long capturedProductCode = productCodeArgumentCaptor.getValue();
        assertThat(capturedProductCode).isEqualTo(productCode);
        verify(productRepo).deleteById(productCode);
    }

    @Test
    void edit_product_correctly_updates_old_product() {
        //given
        Product product = new Product(1L,"Phone","IPhone",99.99,100.0,new ArrayList<>());
        Product updatedProduct = new Product(1L,null,"Samsung",75.99,60.0,new ArrayList<>());
        given(productRepo.findById(any())).willReturn(Optional.of(product));
        //when
        underTest.editProduct(any(),updatedProduct);
        //then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepo).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();
        assertThat(capturedProduct)
                .usingRecursiveComparison()
                .isEqualTo(new Product(1L,"Phone","Samsung",75.99,60.0,new ArrayList<>()));
    }

    @Test
    void lower_stock_method_calls_product_repository_findAllProductsByAvailableQuantityLessThan() {
        //when
        underTest.lowerStock(anyDouble());
        //then
        verify(productRepo).findAllProductsByAvailableQuantityLessThan(anyDouble());
    }
}