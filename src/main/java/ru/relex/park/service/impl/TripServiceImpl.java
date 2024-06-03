package ru.relex.park.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.relex.park.dao.TripDao;
import ru.relex.park.dto.TripDto;
import ru.relex.park.dto.VehicleDto;
import ru.relex.park.dto.filter.TripFilter;
import ru.relex.park.entity.Trip;
import ru.relex.park.mapper.TripMapper;
import ru.relex.park.service.TripService;
import ru.relex.park.service.VehicleService;
import ru.relex.park.util.UserCredentials;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    private final TripDao tripDao;

    private final VehicleService vehicleService;

    private final TripMapper tripMapper;

    private final UserCredentials userCredentials;


    @Override
    public Integer createTrip(TripDto tripDto) {
        Optional<VehicleDto> vehicle = vehicleService.findById(tripDto.getVehicleId());
        if (vehicle.isEmpty()) {
            throw new IllegalStateException("Vehicle with id " + tripDto.getVehicleId() + " not found");
        }

        if (tripDto.getToDate().isBefore(tripDto.getFromDate())) {
            throw new IllegalStateException("To date cannot be before from date");
        }

        List<Trip> trips = tripDao.findByVehicleIdAndDateBetween(tripDto.getVehicleId(),
                tripDto.getFromDate(),
                tripDto.getToDate());
        if (!trips.isEmpty()) {
            throw new IllegalStateException("Vehicle with id " + tripDto.getVehicleId() + " is busy between " + tripDto.getFromDate() + " and " + tripDto.getToDate());
        }

        var trip = tripMapper.toEntity(tripDto);
        var userId = userCredentials.getUserId();
        trip.setUserId(userId);

        return tripDao.save(trip);
    }

    @Override
    public List<TripDto> getAllTripsByFilter(TripFilter tripFilter) {
        Optional<VehicleDto> vehicle = vehicleService.findById(tripFilter.getVehicleId());
        if (vehicle.isEmpty()) {
            throw new IllegalStateException("Vehicle with id " + tripFilter.getVehicleId() + " not found");
        }

        var userId = userCredentials.getUserId();

        return tripDao.getAllByFilter(tripFilter, userId).stream()
                .map(tripMapper::toDto)
                .toList();
    }
}
