package com.SpringBootCamp.finalProject.dto;

import com.SpringBootCamp.finalProject.model.Sale;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter@Setter
public class SalesDayInfoDTO {
    private LocalDate date;
    private Double grandTotal;
    private int numberOfSales;
    private List<Sale> saleList;

    public SalesDayInfoDTO() {
    }

    public SalesDayInfoDTO(LocalDate date, Double grandTotal, int numberOfSales, List<Sale> saleList) {
        this.date = date;
        this.grandTotal = grandTotal;
        this.numberOfSales = numberOfSales;
        this.saleList = saleList;
    }
}
