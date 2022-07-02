package com.example.ftc.customer.command;

import com.example.ftc.customer.domain.CargoType;
import com.example.ftc.customer.domain.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CargoCommand {

    public CargoCommand() {
    }

    private Long id;

    @NotNull
    private CargoType cargoType;
    private String cargoSize;
    private String loadAddress;
    private String unloadAddress;

    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime loadDate;

    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime unloadDate;
    private String loadCustom;
    private String unloadCustom;
    private String description;
    private OrderCommand order;

}
