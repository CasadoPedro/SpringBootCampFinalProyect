package com.SpringBootCamp.finalProject.controller;

import com.SpringBootCamp.finalProject.model.Sale;
import com.SpringBootCamp.finalProject.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SaleController {

    @Autowired
    private ISaleService saleServ;

    @GetMapping("/sale/list")
    public List<Sale> getSales(){
        return saleServ.saleList();
    }

    @GetMapping("/sale/list/{saleCode}")
    public Sale getSales(@PathVariable("saleCode") Long saleCode){
        return saleServ.findSale(saleCode);
    }

    @PostMapping("/sale/create")
    public void createSale(@RequestBody Sale sale){
        saleServ.saveSale(sale);
    }

    @DeleteMapping("/sale/delete/{saleCode}")
    public void deleteSale(@PathVariable Long saleCode){
        saleServ.deleteSale(saleCode);
    }

    @PutMapping("/sale/edit/{saleCode}")
    public void editSale(@PathVariable Long saleCode,
                           @RequestBody Sale sale){
        saleServ.editSale(saleCode,sale);
    }
}
