package ru.relex.park.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.relex.park.dao.TripDao;
import ru.relex.park.dto.TripDto;
import ru.relex.park.dto.VehicleDto;
import ru.relex.park.dto.filter.TripFilter;
import ru.relex.park.mapper.TripMapper;
import ru.relex.park.security.JwtEntity;
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
            throw new IllegalStateException("Vehicle with id " + tripDto.getVehicleId() + " not found");
        }

        if (tripDto.getToDate().isBefore(tripDto.getFromDate())) {
            throw new IllegalStateException("To date cannot be before from date");
        }

        var trip = tripMapper.toEntity(tripDto);

        JwtEntity jwtEntity = getPrincipal();
        trip.setUserId(jwtEntity.getId());

        return tripDao.save(trip);
    }

    @Override
    public List<TripDto> getAllTripsByFilter(TripFilter tripFilter) {
        var principal = getPrincipal();
        var userId = principal.getId();

        return tripDao.getAllByFilter(tripFilter, userId).stream()
                .map(tripMapper::toDto)
                .toList();
    }

    private static JwtEntity getPrincipal() {
        return (JwtEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
