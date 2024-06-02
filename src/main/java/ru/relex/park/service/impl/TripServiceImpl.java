package ru.relex.park.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.relex.park.dto.TripDto;
import ru.relex.park.dto.VehicleDto;
import ru.relex.park.dto.filter.TripFilter;
import ru.relex.park.mapper.TripMapper;
import ru.relex.park.repository.TripDao;
import ru.relex.park.service.TripService;
import ru.relex.park.service.VehicleService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    private final TripDao tripDao;

    private final VehicleService vehicleService;

    private final TripMapper tripMapper;

    @Override
    public Integer createTrip(TripDto tripDto) {
        Optional<VehicleDto> vehicle = vehicleService.findById(tripDto.getVehicleId());
        if (vehicle.isEmpty()) {
            throw new IllegalArgumentException("Vehicle with id " + tripDto.getVehicleId() + " not found");
        }

        if (tripDto.getToDate().isBefore(tripDto.getFromDate())) {
            throw new IllegalArgumentException("To date cannot be before from date");
        }

        // TODO: 02.06.2024 валидация id user

        var trip = tripMapper.toEntity(tripDto);
        return tripDao.save(trip);
    }

    @Override
    public List<TripDto> getAllTripsByFilter(TripFilter tripFilter) {
        return tripDao.getAllByFilter(tripFilter).stream()
                .map(tripMapper::toDto)
                .toList();
    }
}
