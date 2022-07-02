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
    public Cargo findCargoById(Long cargoId) {
        return cargoRepository.findById(cargoId).orElse(null);
    }

    @Override
    public Iterable<Cargo> findAllByOrderId(Long orderId) {
        return cargoRepository.findAllByOrderId(orderId);
    }

    @Override
    public void save(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    @Override
    public void delete(Cargo cargo) {
        cargoRepository.delete(cargo);
    }

    @Override
    public void deleteAllByOrderId(Long orderId) {
        cargoRepository.deleteAllByOrderId(orderId);
    }
}
