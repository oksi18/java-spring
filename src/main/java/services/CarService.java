package services;


import dto.CarDto;
import lombok.RequiredArgsConstructor;
import mapper.CarMapper;
import models.Car;
import org.springframework.stereotype.Service;
import repositories.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public List<CarDto> getAll() {
        return this.carRepository.findAll().stream().map(carMapper::toDto).toList();
    }

    public Optional<CarDto> getById(int id){
        return this.carRepository.findById(id).stream().map(carMapper::toDto).findFirst();
    }

    public void deleteById(int id) {
        this.carRepository.deleteById(id);
    }

    public CarDto create(CarDto carDto){
        return carMapper.toDto(this.carRepository.save(carMapper.toEntity(carDto)));
    }

    public List<CarDto> getByProducer(String producer){
        return this.carRepository.findAllByProducer(producer).stream().map(carMapper::toDto).toList();
    }

    public List<CarDto> getByPower(int power){
        return this.carRepository.findAllByPower(power).stream().map(carMapper::toDto).toList();
    }
}
