package com.example.ftc.customer.repository;

import com.example.ftc.customer.domain.Cargo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CargoRepository extends CrudRepository<Cargo, Long> {

    @Transactional
    void deleteCargoByIdAndOrderId(Long cargoId, Long orderId);

    @Transactional
    void deleteAllByOrderId(Long orderId);

    Optional<Cargo> findCargoByIdAndOrderId(Long cargoId, Long orderId);

    Iterable<Cargo> findAllByOrderId(Long orderId);

}
