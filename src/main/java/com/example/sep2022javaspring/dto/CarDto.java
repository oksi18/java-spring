package com.example.sep2022javaspring.dto;

import com.example.sep2022javaspring.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {
    @JsonView(value = {View.Level1.class})
    private int id;
    @JsonView(value = {View.Level1.class, View.Level2.class, View.Level3.class})
    @NotBlank(message = "model must be")
    @Size(min = 2, max = 20, message = "min - {min}, max - {max}")
    private String model;
    @JsonView(value = {View.Level1.class, View.Level2.class})
    @Min(50)
    @Max(500)
    private int power;
    @JsonView(value = {View.Level1.class, View.Level2.class, View.Level3.class })
    @NotBlank(message = "producer must be")
    @Size(min = 2, max = 20, message = "min - {min}, max - {max}")
    private String producer;
}
