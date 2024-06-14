package com.arbtin.vehicles.car;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<CarRecord> findBookByFrame(String frame);

    CarRecord getCarById(Integer id);
}
