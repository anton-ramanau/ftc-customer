package com.example.ftc.customer.service;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.converter.CargoCommandToCargo;
import com.example.ftc.customer.converter.CargoToCargoCommand;
import com.example.ftc.customer.domain.Cargo;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.repository.CargoRepository;
import org.springframework.stereotype.Service;

@Service
public class CargoServiceImpl implements CargoService{
    private final CargoCommandToCargo cargoCommandToCargo;
    private final CargoToCargoCommand cargoToCargoCommand;
    private final OrderService orderService;
    private final CargoRepository cargoRepository;

    public CargoServiceImpl(CargoCommandToCargo cargoCommandToCargo, CargoToCargoCommand cargoToCargoCommand, OrderService orderService, CargoRepository cargoRepository) {
        this.cargoCommandToCargo = cargoCommandToCargo;
        this.cargoToCargoCommand = cargoToCargoCommand;
        this.orderService = orderService;
        this.cargoRepository = cargoRepository;
    }

    //todo update functionality
    @Override
    public CargoCommand saveCargoCommand(Long orderId, Long userId, CargoCommand cargoCommand) {
        Cargo cargoInput = cargoCommandToCargo.convert(cargoCommand);
        Order order = orderService.findOrderByIdAndUserId(orderId, userId);
        if (order == null) {
            throw new RuntimeException("Can not find order for this user");
        }
        if (cargoInput.getId() != null) {
            return cargoToCargoCommand.convert(updateCargo(cargoInput, orderId));
        } else {
            order.getCargos().add(cargoInput);
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

    @Override
    public Cargo updateCargo(Cargo cargo, Long orderId) {
        Cargo cargoInBase = cargoRepository.findCargoByIdAndOrderId(cargo.getId(), orderId).orElse(null);
        if (cargoInBase == null) {
            throw new RuntimeException("Nothing to update");
        }
        if (cargoInBase.equals(cargo)) {
            return cargo;
        }
        Cargo savedCargo = cargoRepository.save(cargo);
        return savedCargo;
    }

    @Override
    public CargoCommand updateCargo(CargoCommand cargoCommand, Long orderId) {
        Cargo cargo = updateCargo(cargoCommandToCargo.convert(cargoCommand), orderId);
        return cargoToCargoCommand.convert(cargo);
    }

    @Override
    public void deleteCargoByCargoIdAndOrderId(Long cargoId, Long orderId) {
            cargoRepository.deleteCargoByIdAndOrderId(cargoId, orderId);
    }

    @Override
    public CargoCommand findCargoCommandByIdAndOrderId(Long cargoId, Long orderId) {
        Cargo cargo = cargoRepository.findCargoByIdAndOrderId(cargoId, orderId).orElse(null);
        return cargo != null ? cargoToCargoCommand.convert(cargo) : null;
    }
}
