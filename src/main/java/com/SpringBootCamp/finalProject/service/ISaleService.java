package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.dto.BiggestSaleDTO;
import com.SpringBootCamp.finalProject.dto.SalesDayInfoDTO;
import com.SpringBootCamp.finalProject.model.Sale;

import java.time.LocalDate;
import java.util.List;

public interface ISaleService {

    public void saveSale(Sale sale);

    public List<Sale> saleList();

    public Sale findSale(Long saleCode);

    public void deleteSale(Long saleCode);

    public void editSale(Long saleCode,Sale updatedSale);

    public List<Sale> salesByDate(LocalDate date);

    public SalesDayInfoDTO salesDayInfoDTO(LocalDate date);

    public BiggestSaleDTO findBiggestSale();
}
