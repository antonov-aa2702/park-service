package ru.relex.park.mapper;

import org.springframework.stereotype.Component;
import ru.relex.park.dto.VehicleDto;
import ru.relex.park.entity.Vehicle;

@Component
public class VehicleMapper implements Mapper<Vehicle, VehicleDto> {

    @Override
    public VehicleDto toDto(Vehicle from) {
        return VehicleDto.builder()
                .id(from.getId())
                .name(from.getName())
                .year(from.getYear())
                .build();
    }

    @Override
    public Vehicle toEntity(VehicleDto from) {
        return Vehicle.builder()
                .id(from.getId())
                .name(from.getName())
                .year(from.getYear())
                .build();
    }
}
