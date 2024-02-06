package com.SpringBootCamp.finalProject.controller;

import com.SpringBootCamp.finalProject.dto.BiggestSaleDTO;
import com.SpringBootCamp.finalProject.dto.SalesDayInfoDTO;
import com.SpringBootCamp.finalProject.model.Product;
import com.SpringBootCamp.finalProject.model.Sale;
import com.SpringBootCamp.finalProject.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ISaleService saleServ;

    @GetMapping("/list")
    public List<Sale> getSales(){
        return saleServ.saleList();
    }

    @GetMapping("list/{saleCode}")
    public Sale getSales(@PathVariable("saleCode") Long saleCode){
        return saleServ.findSale(saleCode);
    }

    @GetMapping("/products/{saleCode}")
    public List<Product> saleProducts(@PathVariable Long saleCode){
        return saleServ.findSale(saleCode).getProducts_list();
    }
    @GetMapping("/date/{date}")
    public SalesDayInfoDTO dateSalesInfo(@PathVariable LocalDate date){
        return saleServ.salesDayInfoDTO(date);
    }

    @GetMapping("/biggest_sale")
    public BiggestSaleDTO biggestSale(){
        return saleServ.findBiggestSale();
    }

    @PostMapping("/create")
    public void createSale(@RequestBody Sale sale){
        saleServ.saveSale(sale);
    }

    @DeleteMapping("/delete/{saleCode}")
    public void deleteSale(@PathVariable Long saleCode){
        saleServ.deleteSale(saleCode);
    }

    @PutMapping("/edit/{saleCode}")
    public void editSale(@PathVariable Long saleCode,
                           @RequestBody Sale sale){
        saleServ.editSale(saleCode,sale);
    }
}
