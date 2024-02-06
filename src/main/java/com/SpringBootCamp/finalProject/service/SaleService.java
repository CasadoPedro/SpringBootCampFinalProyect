package com.SpringBootCamp.finalProject.service;

import com.SpringBootCamp.finalProject.dto.BiggestSaleDTO;
import com.SpringBootCamp.finalProject.dto.SalesDayInfoDTO;
import com.SpringBootCamp.finalProject.model.Sale;
import com.SpringBootCamp.finalProject.repository.ISaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService implements ISaleService{

    @Autowired
    private ISaleRepository saleRepo;

    ModelMapper modelMapper = new ModelMapper();

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
        return saleRepo.findById(saleCode).orElseThrow(() -> new EntityNotFoundException("Sale with id " + saleCode + " not found"));
    }

    @Override
    public void deleteSale(Long saleCode) {
        saleRepo.deleteById(saleCode);
    }

    @Override
    public void editSale(Long saleCode, Sale updatedSale) {
        Sale sale = saleRepo.findById(saleCode).orElseThrow(() -> new EntityNotFoundException("Sale with id " + saleCode + " not found"));
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updatedSale, sale);
        this.saveSale(sale);
    }

    @Override
    public List<Sale> salesByDate(LocalDate date) {
        return saleRepo.findAllByDate(date);
    }

    @Override
    public SalesDayInfoDTO salesDayInfoDTO(LocalDate date) {
        List<Sale> salesOnDate = this.salesByDate(date);
        Double grandTotal = 0.0;
        for(Sale sale : salesOnDate){
            grandTotal += sale.getTotal_cost();
        }
        SalesDayInfoDTO salesDTO = new SalesDayInfoDTO(date,grandTotal,salesOnDate.size(),salesOnDate);
        return salesDTO;
    }

    @Override
    public BiggestSaleDTO findBiggestSale() {
        Sale biggestSale = saleRepo.findTopSale();
        BiggestSaleDTO biggestDTO = new BiggestSaleDTO(biggestSale.getSaleCode(),
                                                       biggestSale.getTotal_cost(),
                                                       biggestSale.getProducts_list().size(),
                                                       biggestSale.getClient().getName(),
                                                       biggestSale.getClient().getLast_name());
        return biggestDTO;
    }
}
