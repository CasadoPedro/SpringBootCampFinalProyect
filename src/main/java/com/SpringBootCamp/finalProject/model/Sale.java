package com.SpringBootCamp.finalProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Sale date can't be null")
    private LocalDate sale_date;
    @NotNull(message = "Total cost cannot be null")
    @Min(value = 0, message = "Total cost must be greater than or equal to 0")
    @Max(value = 9999, message = "Total cost must be less than or equal to 9999")
    private Double total_cost;
    @NotNull(message = "Client can't be null")
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
