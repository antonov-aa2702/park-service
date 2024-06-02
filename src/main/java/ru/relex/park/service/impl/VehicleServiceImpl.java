package ru.relex.park.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.relex.park.dto.VehicleDto;
import ru.relex.park.mapper.VehicleMapper;
import ru.relex.park.repository.VehicleDao;
import ru.relex.park.service.VehicleService;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleDao vehicleDao;

    private final VehicleMapper vehicleMapper;

    @Override
    public Integer createVehicle(VehicleDto vehicleDto) {
        var entity = vehicleMapper.toEntity(vehicleDto);
        return vehicleDao.save(entity);
    }

    @Override
    public Optional<VehicleDto> findById(Integer vehicleId) {
        return vehicleDao.findById(vehicleId)
                .map(vehicleMapper::toDto);
    }
}
