package com.example.ftc.customer.service;

import com.example.ftc.customer.domain.Cargo;

public interface CargoService {

    void saveCargo(Cargo cargo, Long orderId, Long userId);

    Iterable<Cargo> findAllByOrderIdAndUserId(Long orderId, Long userId);

    void deleteCargoByCargoIdAndOrderId(Long cargoId, Long orderId, Long userId);

    Cargo findCargoByIdAndOrder(Long cargoId, Long orderId, Long userId);

    Cargo findCargoById(Long cargoId);
}
