package com.SpringBootCamp.finalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "invalid product name")
    private String name;
    @NotBlank(message = "invalid brand name")
    private String brand;
    @NotNull(message = "Cost cannot be null")
    @Min(value = 0, message = "Cost must be greater than or equal to 0")
    @Max(value = 9999, message = "Cost must be less than or equal to 9999")
    private Double cost;
    @NotNull(message = "Available quantity cannot be null")
    @Min(value = 0, message = "Available quantity must be greater than or equal to 0")
    @Max(value = 999, message = "Available quantity must be less than or equal to 999")
    private Double available_quantity;

    @JsonIgnore
    @ManyToMany(mappedBy = "products_list",fetch = FetchType.LAZY)
    private List<Sale> sales;
}
