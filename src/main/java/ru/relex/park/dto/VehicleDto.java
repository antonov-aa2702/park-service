package ru.relex.park.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDto {

    private Integer id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Year cannot be null")
    @Min(value = 1900, message = "Year cannot be less than 1900")
    @Max(value = 2024, message = "Year cannot be greater than 2024")
    private Integer year;
}
