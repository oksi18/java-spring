package com.example.sep2022javaspring.services;


import com.example.sep2022javaspring.dto.CarDto;
import com.example.sep2022javaspring.models.Car;
import lombok.RequiredArgsConstructor;
import com.example.sep2022javaspring.mapper.CarMapper;
import org.springframework.stereotype.Service;
import com.example.sep2022javaspring.repositories.CarRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final MailService mailService;

    public List<CarDto> getAll() {
        return this.carRepository.findAll().stream().map(carMapper::toDto).toList();
    }

    public Optional<CarDto> getById(int id){
        return this.carRepository.findById(id).stream().map(carMapper::toDto).findFirst();
    }

    public void deleteById(int id) {
        this.carRepository.deleteById(id);
        this.mailService.notifyDeletedCar(id);
    }

    public CarDto create(CarDto carDto){
        CarDto createdCar = carMapper.toDto(this.carRepository.save(carMapper.toEntity(carDto)));
        this.mailService.notifyCreatedCar(createdCar);
        return createdCar;
    }

    public List<CarDto> getByProducer(String producer){
        return this.carRepository.findAllByProducer(producer).stream().map(carMapper::toDto).toList();
    }

    public List<CarDto> getByPower(int power){
        return this.carRepository.findAllByPower(power).stream().map(carMapper::toDto).toList();
    }

    public CarDto addPhotoByCarId(int id, MultipartFile file) throws IOException {
        Car car = this.carRepository.findById(id).orElseThrow();
        String originalFilename = file.getOriginalFilename();
        String path = System.getProperty("user.home") + File.separator + "cars" + File.separator + originalFilename;
        file.transferTo(new File(path));
        car.setPhoto(originalFilename);
        Car savedCar = this.carRepository.save(car);
        return this.carMapper.toDto(savedCar);
    }
}
