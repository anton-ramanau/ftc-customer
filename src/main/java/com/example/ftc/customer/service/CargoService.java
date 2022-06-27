package com.example.ftc.customer.service;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.domain.Cargo;


public interface CargoService {

    CargoCommand saveCargoCommand(Long orderId, Long userId, CargoCommand cargoCommand);

    Cargo updateCargo(Cargo cargo, Long orderId);

    CargoCommand updateCargo(CargoCommand cargoCommand, Long orderId);

    void deleteCargoByCargoIdAndOrderId(Long cargoId, Long orderId);

    CargoCommand findCargoCommandByIdAndOrderId(Long cargoId, Long orderId);

}
