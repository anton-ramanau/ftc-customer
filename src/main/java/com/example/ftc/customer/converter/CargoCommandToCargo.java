package com.example.ftc.customer.converter;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.domain.Cargo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CargoCommandToCargo implements Converter<CargoCommand, Cargo> {
    private final OrderCommandToOrder orderCommandToOrder;

    public CargoCommandToCargo(OrderCommandToOrder orderCommandToOrder) {
        this.orderCommandToOrder = orderCommandToOrder;
    }

    @Override
    public Cargo convert(CargoCommand cargoCommand) {
        if (cargoCommand == null) {
            return null;
        }
        Cargo cargo = new Cargo();
        cargo.setCargoSize(cargoCommand.getCargoSize());
        cargo.setCargoType(cargoCommand.getCargoType());
        cargo.setId(cargoCommand.getId());
        cargo.setDescription(cargoCommand.getDescription());
        cargo.setLoadAddress(cargoCommand.getLoadAddress());
        cargo.setUnloadAddress(cargoCommand.getUnloadAddress());
        cargo.setLoadCustom(cargoCommand.getLoadCustom());
        cargo.setUnloadCustom(cargoCommand.getUnloadCustom());
        cargo.setLoadDate(cargoCommand.getLoadDate());
        cargo.setUnloadDate(cargoCommand.getUnloadDate());
        cargo.setOrder(orderCommandToOrder.convert(cargoCommand.getOrder()));
        return cargo;
    }
}
