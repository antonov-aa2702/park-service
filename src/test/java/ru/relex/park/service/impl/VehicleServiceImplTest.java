package ru.relex.park.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.relex.park.dao.VehicleDao;
import ru.relex.park.dto.VehicleDto;
import ru.relex.park.entity.Vehicle;
import ru.relex.park.mapper.VehicleMapper;
import ru.relex.park.service.impl.VehicleServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleDao vehicleDao;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;


    @Test
    void testCreateVehicleShouldReturnVehicleId() {
        VehicleDto vehicleDto = new VehicleDto();
        Vehicle entity = new Vehicle();
        when(vehicleMapper.toEntity(vehicleDto))
                .thenReturn(entity);
        when(vehicleDao.save(entity))
                .thenReturn(1);

        Integer vehicleId = vehicleService.createVehicle(vehicleDto);

        assertEquals(1, vehicleId);
    }

    @Test
    void testFindByIdShouldReturnVehicleDto() {
        Integer vehicleId = 1;
        Vehicle entity = new Vehicle();
        VehicleDto expectedVehicleDto = new VehicleDto();
        when(vehicleDao.findById(vehicleId))
                .thenReturn(Optional.of(entity));
        when(vehicleMapper.toDto(entity))
                .thenReturn(expectedVehicleDto);

        Optional<VehicleDto> actualVehicleDto = vehicleService.findById(vehicleId);

        assertEquals(Optional.of(expectedVehicleDto), actualVehicleDto);
    }

    @Test
    void testFindByIdShouldReturnEmptyOptionalIfVehicleNotFound() {
        Integer vehicleId = 1;
        when(vehicleDao.findById(vehicleId)).thenReturn(Optional.empty());

        Optional<VehicleDto> actualVehicleDto = vehicleService.findById(vehicleId);

        assertEquals(Optional.empty(), actualVehicleDto);
    }
}