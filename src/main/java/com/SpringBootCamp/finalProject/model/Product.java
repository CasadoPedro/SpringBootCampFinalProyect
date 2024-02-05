package com.SpringBootCamp.finalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCode;
    private String name;
    private String brand;
    private Double cost;
    private Double available_quantity;
    @JsonIgnore
    @ManyToMany(mappedBy = "products_list",fetch = FetchType.LAZY)
    private List<Sale> sales;
}
