package com.example.ftc.customer.service;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.converter.CargoCommandToCargo;
import com.example.ftc.customer.converter.CargoToCargoCommand;
import com.example.ftc.customer.domain.Cargo;
import com.example.ftc.customer.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class CargoServiceImpl implements CargoService{
    private final CargoCommandToCargo cargoCommandToCargo;
    private final CargoToCargoCommand cargoToCargoCommand;
    private final OrderService orderService;

    public CargoServiceImpl(CargoCommandToCargo cargoCommandToCargo, CargoToCargoCommand cargoToCargoCommand, OrderService orderService) {
        this.cargoCommandToCargo = cargoCommandToCargo;
        this.cargoToCargoCommand = cargoToCargoCommand;
        this.orderService = orderService;
    }

    //todo update functionality
    @Override
    public CargoCommand saveCargoCommand(String username, Long orderId, CargoCommand cargoCommand) {
        Order order = orderService.findOrderByUserNameAndOrderId(username, orderId);
        order.getCargos().add(cargoCommandToCargo.convert(cargoCommand));
        Order savedOrder = orderService.saveOrder(order);
        Cargo savedCargo = savedOrder.getCargos().stream()
                .filter(cargo -> cargo.getCargoSize().equals(cargoCommand.getCargoSize()))
//                todo add defaultcargotype
//                .filter(cargo -> cargo.getCargoType().equals(cargoCommand.getCargoType()))
                .filter(cargo -> cargo.getLoadAddress().equals(cargoCommand.getLoadAddress()))
                .filter(cargo -> cargo.getUnloadAddress().equals(cargoCommand.getUnloadAddress()))
//                todo add correct find functionality
//                .filter(cargo -> cargo.getLoadDate().isEqual(cargoCommand.getLoadDate()))
//                .filter(cargo -> cargo.getUnloadDate().isEqual(cargoCommand.getUnloadDate()))
                .filter(cargo -> cargo.getDescription().equals(cargoCommand.getDescription()))
                .filter(cargo -> cargo.getLoadCustom().equals(cargoCommand.getLoadCustom()))
                .filter(cargo -> cargo.getUnloadCustom().equals(cargoCommand.getUnloadCustom()))
                .findFirst().orElse(null);
        return cargoToCargoCommand.convert(savedCargo);




    }
}
