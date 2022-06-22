package com.example.ftc.customer.command;

import com.example.ftc.customer.domain.CargoType;
import com.example.ftc.customer.domain.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CargoCommand {

    public CargoCommand() {
    }


    private Long id;
    private CargoType cargoType;
    private String cargoSize;
    private String loadAddress;
    private String unloadAddress;
    private LocalDate loadDate;
    private LocalDate unloadDate;
    private String loadCustom;
    private String unloadCustom;
    private String description;

}
