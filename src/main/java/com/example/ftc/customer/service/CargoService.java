package com.example.ftc.customer.service;

import com.example.ftc.customer.domain.Cargo;

public interface CargoService {

    Cargo findCargoById(Long cargoId);

    Iterable<Cargo> findAllByOrderId(Long orderId);

    void save(Cargo cargo);

    void delete(Cargo cargo);

}
