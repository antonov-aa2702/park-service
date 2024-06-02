package ru.relex.park.dto.filter;

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
public class TripFilter {

    @NotNull(message = "Vehicle id cannot be null")
    private Integer vehicleId;

    @NotNull(message = "From date cannot be null")
    private Integer fromDate;

    @NotNull(message = "To date cannot be null")
    private Integer toDate;
}

