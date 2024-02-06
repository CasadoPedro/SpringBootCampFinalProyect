package com.SpringBootCamp.finalProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BiggestSaleDTO {

    private Long saleCode;
    private Double grandTotal;
    private int itemQuantity;
    private String clientName;
    private String clientLastName;

    public BiggestSaleDTO(Long saleCode, Double grandTotal, int itemQuantity, String clientName, String clientLastName) {
        this.saleCode = saleCode;
        this.grandTotal = grandTotal;
        this.itemQuantity = itemQuantity;
        this.clientName = clientName;
        this.clientLastName = clientLastName;
    }
}
