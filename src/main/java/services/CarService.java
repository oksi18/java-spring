package services;


import lombok.RequiredArgsConstructor;
import models.Car;
import org.springframework.stereotype.Service;
import repositories.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public List<Car> getAll() {
        return this.carRepository.findAll();
    }

    public Optional<Car> getById(int id){
        return this.carRepository.findById(id);
    }

    public void deleteById(int id) {
        this.carRepository.deleteById(id);
    }

    public Car create(Car car){
        return this.carRepository.save(car);
    }

    public List<Car> getByProducer(String producer){
        return this.carRepository.findAllByProducer(producer);
    }

    public List<Car> getByPower(int power){
        return this.carRepository.findAllByPower(power);
    }
}
