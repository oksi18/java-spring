package com.example.sep2022javaspring.controllers;

import com.example.sep2022javaspring.dto.CarDto;
import com.example.sep2022javaspring.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import com.example.sep2022javaspring.repositories.CarRepository;
import com.example.sep2022javaspring.services.CarService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cars")
public class CarController  {
    private final CarService carService;
    private final CarRepository carRepository;

    @JsonView({View.Level3.class})
    @GetMapping
    public ResponseEntity<List<CarDto>> getAll(){
        return ResponseEntity.ok(this.carService.getAll());
    }

    @Secured("ROLE_SELLER")
    @PostMapping 
    public ResponseEntity<CarDto> create(@RequestBody @Valid CarDto carDto){
        CarDto car = carService.create(carDto);
        return ResponseEntity.ok(car);

    }

    @JsonView(View.Level1.class  )
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getById(@PathVariable int id){
        return ResponseEntity.of(this.carService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id){
        this.carService.deleteById(id);
    }

    @JsonView({View.Level2.class})
    @GetMapping("/power/{value}")
    public  ResponseEntity<List<CarDto>> getByPower(@PathVariable int value){
        return ResponseEntity.ok(this.carService.getByPower(value));
    }

    @JsonView({View.Level2.class})
    @GetMapping("/producer/{value}")
    public  ResponseEntity<List<CarDto>> getByProducer(@PathVariable String    value){
        return ResponseEntity.ok(this.carService.getByProducer(value));
    }

    @SneakyThrows
    @JsonView(View.Level1.class)
    @PostMapping("/{id}/photo")
    public ResponseEntity<CarDto> addPhotoToCarById(@PathVariable int id,   MultipartFile photo){
        return ResponseEntity.ok(this.carService.addPhotoByCarId(id, photo));
    }

}
