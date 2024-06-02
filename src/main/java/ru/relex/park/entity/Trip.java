package ru.relex.park.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    private Integer id;

    private Integer userId;

    private Integer vehicleId;

    private Integer distance;

    private LocalDate fromDate;

    private LocalDate toDate;
}
