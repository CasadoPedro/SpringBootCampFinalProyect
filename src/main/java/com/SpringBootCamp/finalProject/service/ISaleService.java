package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.model.Sale;

import java.util.List;

public interface ISaleService {

    public void saveSale(Sale sale);

    public List<Sale> saleList();

    public Sale findSale(Long saleCode);

    public void deleteSale(Long saleCode);

    public void editSale(Long saleCode,Sale updatedSale);
}
