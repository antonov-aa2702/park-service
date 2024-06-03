package ru.relex.park.service;

import ru.relex.park.dto.VehicleDto;

import java.util.Optional;

public interface VehicleService {

    Integer createVehicle(VehicleDto vehicleDto);

    Optional<VehicleDto> findById(Integer vehicleId);
}
