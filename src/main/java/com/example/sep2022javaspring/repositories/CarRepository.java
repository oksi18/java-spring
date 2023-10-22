package com.example.sep2022javaspring.repositories;

import com.example.sep2022javaspring.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository< Car, Integer> {

    List<Car> findAllByProducer(String producer);
    List<Car> findAllByPower(int power);
}
