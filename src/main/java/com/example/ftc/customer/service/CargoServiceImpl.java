package com.example.ftc.customer.service;

import com.example.ftc.customer.converter.CargoToCargoCommand;
import com.example.ftc.customer.domain.Cargo;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.repository.CargoRepository;
import org.springframework.stereotype.Service;

@Service
public class CargoServiceImpl implements CargoService{
    private final CargoToCargoCommand cargoToCargoCommand;
    private final OrderService orderService;
    private final CargoRepository cargoRepository;

    public CargoServiceImpl(CargoToCargoCommand cargoToCargoCommand, OrderService orderService, CargoRepository cargoRepository) {
        this.cargoToCargoCommand = cargoToCargoCommand;
        this.orderService = orderService;
        this.cargoRepository = cargoRepository;
    }

    @Override
    public void saveCargo(Cargo cargo, Long orderId, Long userId) {
        if (cargo == null) {
            throw new NullPointerException("Cargo is null");
        }
        //todo optimise checking access session user to order
        Order order = orderService.findOrderByIdAndUserId(orderId, userId);
        if (cargo.getId() == null) {
            cargo.setOrder(order);
            cargoRepository.save(cargo);
        } else {
            Cargo cargoFromDB = cargoRepository.findById(cargo.getId()).orElse(null);
            if (cargoFromDB != null) {
                if (cargoFromDB.getOrder().getId().equals(order.getId())) {
                    //todo update eachfield
                    cargoFromDB.setCargoType(cargo.getCargoType());
                    cargoFromDB.setCargoSize(cargo.getCargoSize());
                    cargoFromDB.setCargoType(cargo.getCargoType());
                    cargoFromDB.setId(cargo.getId());
                    cargoFromDB.setDescription(cargo.getDescription());
                    cargoFromDB.setLoadAddress(cargo.getLoadAddress());
                    cargoFromDB.setUnloadAddress(cargo.getUnloadAddress());
                    cargoFromDB.setLoadCustom(cargo.getLoadCustom());
                    cargoFromDB.setUnloadCustom(cargo.getUnloadCustom());
                    cargoFromDB.setLoadDate(cargo.getLoadDate());
                    cargoFromDB.setUnloadDate(cargo.getUnloadDate());
                    cargoFromDB.setOrder(order);
                    cargoRepository.save(cargoFromDB);
                } else {
                    throw new IllegalArgumentException("Order is not owner of cargo");
                }
            } else {
                throw new NullPointerException("Cargo doesn't exists");
            }
        }
    }

    @Override
    public Iterable<Cargo> findAllByOrderId(Long orderId) {
        Iterable<Cargo> cargos = cargoRepository.findAllByOrderId(orderId);
        if (cargos != null) {
            return cargos;
        } else {
            throw new NullPointerException("Cargoes didn't find");
        }
    }

    @Override
    public void deleteCargoByCargoIdAndOrderId(Long cargoId, Long orderId) {
            cargoRepository.deleteCargoByIdAndOrderId(cargoId, orderId);
    }

    //todo do something mit user access
    @Override
    public Cargo findCargoByIdAndOrder(Long cargoId, Long orderId) {
        return cargoRepository.findCargoByIdAndOrderId(cargoId, orderId)
                .orElseThrow(() -> new NullPointerException("Cargo doesn't exists"));
    }
}
