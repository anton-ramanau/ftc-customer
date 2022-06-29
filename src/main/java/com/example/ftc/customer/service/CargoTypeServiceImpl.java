package com.example.ftc.customer.service;

import com.example.ftc.customer.domain.CargoType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class CargoTypeServiceImpl implements CargoTypeService {
    @Override
    public Set<CargoType> findAll() {
        Set<CargoType> cargoTypes = new HashSet<>();
        Collections.addAll(cargoTypes, CargoType.values());
        return cargoTypes;
    }
}
