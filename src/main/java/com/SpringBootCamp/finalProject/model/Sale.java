package com.SpringBootCamp.finalProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleCode;
    private LocalDate sale_date;
    private Double total_cost;
    @ManyToOne
    @JoinColumn(name = "clientId",referencedColumnName = "clientId")
    private Client client;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sale_products",
            joinColumns = @JoinColumn(name = "saleCode"),
            inverseJoinColumns = @JoinColumn(name = "productCode")
    )
    private List<Product> products_list;

}
