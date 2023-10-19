package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {
    private int id;
    @NotBlank(message = "model must be")
    @Size(min = 2, max = 20, message = "min - {min}, max - {max}")
    private String model;
    @Size(min = 50, message = "min power - {min}")
    private int power;
    @NotBlank(message = "producer must be")
    @Size(min = 2, max = 20, message = "min - {min}, max - {max}")
    private String producer;
}
