package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.dto.BiggestSaleDTO;
import com.SpringBootCamp.finalProject.dto.SalesDayInfoDTO;
import com.SpringBootCamp.finalProject.exception.OutOfStockException;
import com.SpringBootCamp.finalProject.model.Client;
import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.model.Sale;
import com.SpringBootCamp.finalProject.repository.ISaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private ISaleRepository saleRepo;
    @Mock
    private ClientService clientServ;
    @Mock
    private ProductService productServ;
    private SaleService underTest;

    @BeforeEach
    void setUp() {
        underTest = new SaleService(saleRepo, clientServ, productServ);
    }

    private Sale createDummySale() {
        Product dummyProduct = new Product(999L, "Dummy Product", "Brand", 10.0, 5.0, new ArrayList<>());

        Sale dummySale = new Sale(1L,
                LocalDate.now(),
                10.0,
                new Client(999L, "Invalid", "Client", "invalid@example.com", new ArrayList<>()),
                Collections.singletonList(dummyProduct));

        return dummySale;
    }


    @Test
    void sale_list_method_calls_sale_repository_find_all() {
        //when
        underTest.saleList();
        //then
        verify(saleRepo).findAll();
    }

    @Test
    void find_sale_with_invalid_id_throws_EntityNotFoundException() {
        //given
        given(saleRepo.findById(any())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(()-> underTest.findSale(anyLong()))
                .isInstanceOf(EntityNotFoundException.class)
                        .hasMessageContaining("Sale with id ");
        verify(saleRepo).findById(anyLong());
    }

    @Test
    void save_sale_with_invalid_client_id_throws_EntityNotFoundException() {
        // given
        Sale dummySale = createDummySale();
        Long clientId = dummySale.getClient().getClientId();
        given(clientServ.findClient(clientId)).willReturn(null);
        // when
        // then
        assertThatThrownBy(() -> underTest.saveSale(dummySale))
                .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Client with id " + clientId + " not found");
        verify(saleRepo, never()).save(any());
    }
    @Test
    void save_sale_with_invalid_product_code_in_product_list_throws_EntityNotFoundException() {
        // given
        Sale dummySale = createDummySale();
        given(clientServ.findClient(anyLong())).willReturn(new Client());
        // when
        // then
        assertThatThrownBy(() -> underTest.saveSale(dummySale))
                .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Product with id " + dummySale.getProducts_list().getFirst().getProductCode() + " not found");
        verify(saleRepo, never()).save(any());
    }
    @Test
    void save_sale_with_out_of_stock_product_in_product_list_throws_OutOfStockException() {
        // given
        Sale dummySale = createDummySale();
        Product saleProduct = dummySale.getProducts_list().getFirst();
        saleProduct.setAvailable_quantity(0.0);
        given(clientServ.findClient(anyLong())).willReturn(new Client());
        given(productServ.findProduct(anyLong())).willReturn(saleProduct);
        // when
        // then
        assertThatThrownBy(() -> underTest.saveSale(dummySale))
                .isInstanceOf(OutOfStockException.class)
                    .hasMessageContaining("The product with code " + saleProduct.getProductCode() + " is out of stock");
        verify(saleRepo, never()).save(any());
    }
    @Test
    void save_sale_reduces_product_available_quantity_by_one(){
        //given
        Sale dummySale = createDummySale();
        Product saleProduct = dummySale.getProducts_list().getFirst();
        Double originalQuantity = saleProduct.getAvailable_quantity();
        given(clientServ.findClient(anyLong())).willReturn(new Client());
        given(productServ.findProduct(anyLong())).willReturn(saleProduct);
        //when
        underTest.saveSale(dummySale);
        //then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productServ).editProduct(any(),productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();
        assertThat(capturedProduct.getAvailable_quantity()).isEqualTo(originalQuantity-1);
    }

    @Test
    void delete_sale_calls_repository_delete_by_id_with_valid_sale_code() {
        //given
        Long saleCode = 999L;
        //when
        underTest.deleteSale(saleCode);
        //then
        ArgumentCaptor<Long> productCodeArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(saleRepo).deleteById(productCodeArgumentCaptor.capture());
        Long capturedProductCode = productCodeArgumentCaptor.getValue();
        assertThat(capturedProductCode).isEqualTo(saleCode);
    }

    @Test
    void edit_sale_correctly_updates_old_sale() {
        //given(create a new Sale with a different client and productList)
        Sale dummySale = createDummySale();
        Product updatedProduct = new Product(999L,"DummyProduct Updated","Updated Brand",99.99,10.0,new ArrayList<>());
        Sale dummySaleUpdated = new Sale(1L,
                LocalDate.now(),
                0.0,
                new Client(999L, "nameUpdated", "Client", "invalid_updated@example.com", new ArrayList<>()),
                Collections.singletonList(updatedProduct));
        //necessary configuration for the calls in sale service
        given(saleRepo.findById(any())).willReturn(Optional.of(dummySale));
        given(clientServ.findClient(anyLong())).willReturn(new Client());
        given(productServ.findProduct(anyLong())).willReturn(updatedProduct);
        //when
        underTest.editSale(any(),dummySaleUpdated);
        //then(the sale that gets saved is compared to the sale that we created with available_quantity reduced by one)
        ArgumentCaptor<Sale> productArgumentCaptor = ArgumentCaptor.forClass(Sale.class);
        verify(saleRepo).save(productArgumentCaptor.capture());
        Sale capturedSale = productArgumentCaptor.getValue();
        assertThat(capturedSale)
                .usingRecursiveComparison()
                .isEqualTo(new Sale(1L,LocalDate.now(),99.99,
                        new Client(999L,"nameUpdated","Client","invalid_updated@example.com",new ArrayList<>()),
                        Collections.singletonList(new Product(999L,"DummyProduct Updated",
                                "Updated Brand",99.99,9.0,new ArrayList<>()))));
    }

    @Test
    void sales_by_date_method_calls_sale_repository_findAllByDate_with_correct_date() {
        //given
        LocalDate date = LocalDate.parse("2023-06-12");
        //when
        underTest.salesByDate(date);
        //then
        ArgumentCaptor<LocalDate> saleDateArgumentCaptor = ArgumentCaptor.forClass(LocalDate.class);
        verify(saleRepo).findAllByDate(saleDateArgumentCaptor.capture());
        LocalDate capturedSaleDate = saleDateArgumentCaptor.getValue();
        assertThat(capturedSaleDate).isEqualTo(date);
    }

    @Test
    void salesDayInfoDTO_returns_correct_dto() {
        //given
        LocalDate currentDate = LocalDate.now();
        Sale dummySale = createDummySale();
        Product dummyProduct2 = new Product(888L, "Dummy Product", "Brand", 50.0, 5.0, new ArrayList<>());
        Sale dummySale2 = new Sale(888L, currentDate,50.0,dummySale.getClient(),Collections.singletonList(dummyProduct2));
        List<Sale> saleList = new ArrayList<>();
        saleList.add(dummySale);
        saleList.add(dummySale2);
        given(saleRepo.findAllByDate(currentDate)).willReturn(saleList);
        //when
        SalesDayInfoDTO dto = underTest.salesDayInfoDTO(currentDate);
        //then
        assertThat(dto).usingRecursiveComparison().isEqualTo(new SalesDayInfoDTO(currentDate,60.0,2,saleList));
    }

    @Test
    void findBiggestSale_returns_correct_dto() {
        //given
        Sale dummySale = createDummySale();
        given(saleRepo.findTopSale()).willReturn(dummySale);
        //when
        BiggestSaleDTO dto = underTest.findBiggestSale();
        //then
        assertThat(dto).usingRecursiveComparison()
                .isEqualTo(new BiggestSaleDTO(1L,10.0,1,"Invalid","Client"));
    }
}