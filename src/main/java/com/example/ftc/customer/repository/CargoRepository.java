package com.example.ftc.customer.repository;

import com.example.ftc.customer.domain.Cargo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CargoRepository extends CrudRepository<Cargo, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM cargoes WHERE id = :id AND order_id = :orderId")
    void deleteCargoByIdAndOrderId(@Param("id") Long cargoId, @Param("orderId") Long orderId);

    @Query(nativeQuery = true, value = "SELECT * FROM cargoes WHERE id = ?1 AND order_id = ?2")
    Optional<Cargo> findCargoByIdAndOrderId(Long cargoId, Long orderId);

}
