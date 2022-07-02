package com.example.ftc.customer.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "cargoes")
public class Cargo {

    public Cargo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cargo_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo_type")
    private CargoType cargoType;

    @Column(name = "cargo_size")
    private String cargoSize;

    @Column(name = "load_address")
    private String loadAddress;

    @Column(name = "unload_address")
    private String unloadAddress;

    @Column(name = "load_date")
    private LocalDateTime loadDate;

    @Column(name = "unload_date")
    private LocalDateTime unloadDate;

    @Column(name = "load_custom")
    private String loadCustom;

    @Column(name = "unload_custom")
    private String unloadCustom;

    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;



}
