package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Sale;
import com.SpringBootCamp.finalProject.model.Sale;
import com.SpringBootCamp.finalProject.repository.ISaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService implements ISaleService{

    @Autowired
    private ISaleRepository saleRepo;

    @Override
    public void saveSale(Sale sale) {
        saleRepo.save(sale);
    }

    @Override
    public List<Sale> saleList() {
        return saleRepo.findAll();
    }

    @Override
    public Sale findSale(Long saleCode) {
        return saleRepo.findById(saleCode).orElse(null);
    }

    @Override
    public void deleteSale(Long saleCode) {
        saleRepo.deleteById(saleCode);
    }

    @Override
    public void editSale(Long saleCode, Sale updatedSale) {
        Sale sale = this.findSale(saleCode);
        sale.setSale_date(updatedSale.getSale_date());
        sale.setTotal_cost(updatedSale.getTotal_cost());
        sale.setClient(updatedSale.getClient());
        sale.setProducts_list(updatedSale.getProducts_list());
        this.saveSale(sale);
    }
}
