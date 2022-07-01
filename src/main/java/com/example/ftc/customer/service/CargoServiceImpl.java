package com.example.ftc.customer.service;

import com.example.ftc.customer.converter.CargoToCargoCommand;
import com.example.ftc.customer.domain.Cargo;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.exception.CargoNotFoundException;
import com.example.ftc.customer.exception.OrderNotFoundException;
import com.example.ftc.customer.repository.CargoRepository;
import org.springframework.stereotype.Service;

@Service
public class CargoServiceImpl implements CargoService {
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
        if (cargo.getId() == null) {
            Order order = orderService.findOrderByIdAndUserId(orderId, userId);
            if (order == null) {
                throw new OrderNotFoundException(orderId);
            }
            cargo.setOrder(order);
            cargoRepository.save(cargo);
        } else {
            Cargo cargoFromDB = cargoRepository.findById(cargo.getId()).orElse(null);
            if (cargoFromDB == null) {
                throw new CargoNotFoundException(cargo.getId(), orderId);
            }
            if (!cargoFromDB.getOrder().getId().equals(orderId)) {
                throw new OrderNotFoundException(orderId);
            }
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
            cargoRepository.save(cargoFromDB);
        }
    }

    @Override
    public Iterable<Cargo> findAllByOrderIdAndUserId(Long orderId, Long userId) {
        Order order = orderService.findOrderByIdAndUserId(orderId, userId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        Iterable<Cargo> cargos = cargoRepository.findAllByOrderId(orderId);
        return cargos;
    }

    @Override
    public void deleteCargoByCargoIdAndOrderId(Long cargoId, Long orderId, Long userId) {
        Cargo cargo = findCargoByIdAndOrder(cargoId, orderId, userId);
        cargoRepository.delete(cargo);
    }

    @Override
    public Cargo findCargoByIdAndOrder(Long cargoId, Long orderId, Long userId) {
        Cargo cargo = cargoRepository.findById(cargoId).orElse(null);
        if (cargo == null) {
            throw new CargoNotFoundException(cargoId, orderId);
        }
        if (!cargo.getOrder().getId().equals(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        if (!cargo.getOrder().getUser().getId().equals(userId)) {
            throw new OrderNotFoundException(orderId);
        }
        return cargo;
    }
}
