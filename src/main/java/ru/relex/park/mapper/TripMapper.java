package ru.relex.park.mapper;

import org.springframework.stereotype.Component;
import ru.relex.park.dto.TripDto;
import ru.relex.park.entity.Trip;

@Component
public class TripMapper implements Mapper<Trip, TripDto> {

    @Override
    public Trip toEntity(TripDto tripDto) {
        return Trip.builder()
                .vehicleId(tripDto.getVehicleId())
                .distance(tripDto.getDistance())
                .fromDate(tripDto.getFromDate())
                .toDate(tripDto.getToDate())
                .build();
    }

    @Override
    public TripDto toDto(Trip trip) {
        return TripDto.builder()
                .id(trip.getId())
                .vehicleId(trip.getVehicleId())
                .distance(trip.getDistance())
                .fromDate(trip.getFromDate())
                .toDate(trip.getToDate())
                .build();
    }
}
