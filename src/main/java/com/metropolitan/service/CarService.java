package com.metropolitan.service;

import com.metropolitan.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {


    List<Car> findAll();

    Optional<Car> findById(Long id);

    Car save(Car car);

    void deleteById(Long id);
}
