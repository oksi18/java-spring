package mapper;

import dto.CarDto;
import models.Car;

public class CarMapper {
    public CarDto toDto(Car car){
        return CarDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .power(car.getPower())
                .producer(car.getProducer())
                .build();
    }

    public Car toEntity(CarDto carDto) {
        Car car = new Car();
        car.setModel(carDto.getModel());
        car.setPower(carDto.getPower());
        car.setProducer(carDto.getProducer());
       return car;
    }
}
