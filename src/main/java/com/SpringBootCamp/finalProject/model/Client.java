package com.SpringBootCamp.finalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @NotBlank(message = "invalid client name")
    private String name;

    @NotBlank(message = "invalid client last_name")
    private String last_name;

    @Column(unique = true)
    @Email(message = "invalid email address")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Sale> sales_done;
}
