package ru.relex.park.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.relex.park.dao.TripDao;
import ru.relex.park.dto.TripDto;
import ru.relex.park.dto.VehicleDto;
import ru.relex.park.dto.filter.TripFilter;
import ru.relex.park.entity.Trip;
import ru.relex.park.mapper.TripMapper;
import ru.relex.park.service.VehicleService;
import ru.relex.park.util.UserCredentials;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {

    @Mock
    private TripDao tripDao;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private TripMapper tripMapper;

    @Mock
    private UserCredentials userCredentials;

    @InjectMocks
    private TripServiceImpl tripService;

    @Test
    void testCreateTripSuccess() {
        TripDto tripDto = getDefaultTripDto();
        when(vehicleService.findById(tripDto.getVehicleId()))
                .thenReturn(Optional.of(new VehicleDto()));
        when(tripMapper.toEntity(tripDto))
                .thenReturn(new Trip());
        when(userCredentials.getUserId())
                .thenReturn(1);
        when(tripDao.save(any(Trip.class)))
                .thenReturn(1);

        Integer result = tripService.createTrip(tripDto);

        assertEquals(1, result);
        verify(tripDao).save(any(Trip.class));
    }

    @Test
    void testCreateTripIfVehicleNotFound() {
        TripDto tripDto = getDefaultTripDto();
        when(vehicleService.findById(tripDto.getVehicleId()))
                .thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            tripService.createTrip(tripDto);
        });

        assertEquals("Vehicle with id 1 not found", exception.getMessage());
    }

    @Test
    void testAllTripsByFilterShouldReturnTrips() {
        TripFilter tripFilter = new TripFilter();
        tripFilter.setVehicleId(1);

        VehicleDto vehicleDto = new VehicleDto();
        when(vehicleService.findById(tripFilter.getVehicleId()))
                .thenReturn(Optional.of(vehicleDto));

        int userId = 1;
        when(userCredentials.getUserId()).thenReturn(userId);

        List<Trip> trips = List.of(new Trip(), new Trip());
        when(tripDao.getAllByFilter(tripFilter, userId)).thenReturn(trips);

        List<TripDto> expectedTrips = List.of(new TripDto(), new TripDto());
        when(tripMapper.toDto(trips.get(0)))
                .thenReturn(expectedTrips.get(0));
        when(tripMapper.toDto(trips.get(1)))
                .thenReturn(expectedTrips.get(1));

        List<TripDto> actualTrips = tripService.getAllTripsByFilter(tripFilter);

        assertEquals(expectedTrips, actualTrips);
    }

    @Test
    void testGetAllTripsByFilterShouldThrowExceptionIfVehicleNotFound() {
        // Given
        TripFilter tripFilter = new TripFilter();
        tripFilter.setVehicleId(1);

        when(vehicleService.findById(tripFilter.getVehicleId())).thenReturn(Optional.empty());

        // When
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            tripService.getAllTripsByFilter(tripFilter);
        });

        // Then
        assertEquals("Vehicle with id 1 not found", exception.getMessage());
    }

    private static TripDto getDefaultTripDto() {
        return TripDto.builder().vehicleId(1)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now().plusDays(1))
                .build();
    }
}