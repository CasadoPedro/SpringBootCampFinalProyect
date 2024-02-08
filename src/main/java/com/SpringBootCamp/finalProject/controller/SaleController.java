package com.SpringBootCamp.finalProject.controller;

import com.SpringBootCamp.finalProject.dto.BiggestSaleDTO;
import com.SpringBootCamp.finalProject.dto.SalesDayInfoDTO;
import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.model.Sale;
import com.SpringBootCamp.finalProject.service.ISaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ISaleService saleServ;

    @GetMapping("/list")
    public ResponseEntity<List<Sale>> getAllSales(){
        return ResponseEntity.ok(saleServ.saleList());
    }

    @GetMapping("list/{saleCode}")
    public ResponseEntity<Sale> getSale(@PathVariable("saleCode") Long saleCode){
        return ResponseEntity.ok(saleServ.findSale(saleCode));
    }

    @GetMapping("/products/{saleCode}")
    public ResponseEntity<List<Product>> saleProducts(@PathVariable Long saleCode){
        return ResponseEntity.ok(saleServ.findSale(saleCode).getProducts_list());
    }
    @GetMapping("/date/{date}")
    public ResponseEntity<SalesDayInfoDTO> dateSalesInfo(@PathVariable LocalDate date){
        return ResponseEntity.ok(saleServ.salesDayInfoDTO(date));
    }

    @GetMapping("/biggest_sale")
    public ResponseEntity<BiggestSaleDTO> biggestSale(){
        return ResponseEntity.ok(saleServ.findBiggestSale());
    }

    @PostMapping("/create")
    public ResponseEntity<Sale> createSale(@RequestBody @Valid Sale sale){
        return new ResponseEntity<>(saleServ.saveSale(sale), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{saleCode}")
    public ResponseEntity deleteSale(@PathVariable Long saleCode){
        saleServ.deleteSale(saleCode);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{saleCode}")
    public ResponseEntity<Sale> editSale(@PathVariable Long saleCode,
                           @RequestBody Sale sale){
        return new ResponseEntity<>(saleServ.editSale(saleCode,sale), HttpStatus.OK);
    }
}
