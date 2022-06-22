package com.example.ftc.customer.service;

import com.example.ftc.customer.command.CargoCommand;


public interface CargoService {

    CargoCommand saveCargoCommand(String username, Long orderId, CargoCommand cargoCommand);
}
