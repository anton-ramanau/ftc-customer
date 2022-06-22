package com.example.ftc.customer.service;

import com.example.ftc.customer.command.CargoCommand;


public interface CargoService {

    CargoCommand saveCargoCommand(Long orderId, Long userId, CargoCommand cargoCommand);
}
