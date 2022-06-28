package com.example.ftc.customer.service;

import com.example.ftc.customer.domain.Cargo;

public interface CargoService {

    void saveCargo(Cargo cargo, Long orderId, Long userId);

    Iterable<Cargo> findAllByOrderId(Long orderId);

    void deleteCargoByCargoIdAndOrderId(Long cargoId, Long orderId);

    Cargo findCargoByIdAndOrder(Long cargoId, Long orderId);
}
