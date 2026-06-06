package com.metropolitan.service;

import com.metropolitan.entity.Car;
import com.metropolitan.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CarServiceTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setup() {
        carRepository.deleteAll();
        carRepository.saveAll(List.of(
                new Car(null, "Toyota", "Yaris", 2020, null),
                new Car(null, "Ford", "Focus", 2018, null)
        ));
    }

    @Test
    void testFindAllCars() {
        List<Car> cars = carService.findAll();
        assertEquals(2, cars.size());
        assertEquals("Toyota", cars.get(0).getBrand());
    }

    @Test
    void testAddCar() {
        Car car = new Car(null, "Honda", "Civic", 2022, null);
        Car saved = carService.save(car);
        assertEquals("Honda", saved.getBrand());
        assertEquals(3, carService.findAll().size());
    }
}
