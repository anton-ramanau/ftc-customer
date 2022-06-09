package com.example.ftc.customer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cargoes")
public class Cargo {

    public Cargo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CargoType cargoType;

    @ManyToOne
    private Order order;

    private String cargoSize;
    private String loadAddress;
    private String unloadAddress;
    private LocalDate loadDate;
    private LocalDate unloadDate;
    private String loadCustom;
    private String unloadCustom;
    private String description;




}
