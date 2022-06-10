package com.example.ftc.customer.converter;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.domain.Cargo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CargoToCargoCommand implements Converter<Cargo, CargoCommand> {

    @Override
    public CargoCommand convert(Cargo cargo) {
        if (cargo == null) {
            return null;
        }

        CargoCommand cargoCommand = new CargoCommand();

        cargoCommand.setCargoSize(cargo.getCargoSize());
        cargoCommand.setCargoType(cargo.getCargoType());
        cargoCommand.setId(cargo.getId());
        cargoCommand.setDescription(cargo.getDescription());
        cargoCommand.setLoadAddress(cargo.getLoadAddress());
        cargoCommand.setUnloadAddress(cargo.getUnloadAddress());
        cargoCommand.setLoadCustom(cargo.getLoadCustom());
        cargoCommand.setUnloadCustom(cargo.getUnloadCustom());
        cargoCommand.setLoadDate(cargo.getLoadDate());
        cargoCommand.setUnloadDate(cargo.getUnloadDate());

        return cargoCommand;
    }
}
