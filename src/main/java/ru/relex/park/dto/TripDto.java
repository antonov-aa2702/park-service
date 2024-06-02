package ru.relex.park.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDto {

    private Integer id;

    @NotNull(message = "User id cannot be null")
    private Integer userId;

    @NotNull(message = "Vehicle id cannot be null")
    private Integer vehicleId;

    @NotNull(message = "Distance cannot be null")
    @Min(value = 0, message = "Distance cannot be negative")
    private Integer distance;

    @NotNull(message = "From date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    @NotNull(message = "To date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;
}

