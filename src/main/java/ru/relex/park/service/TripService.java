package ru.relex.park.service;

import ru.relex.park.dto.TripDto;
import ru.relex.park.dto.filter.TripFilter;

import java.util.List;

public interface TripService {

    Integer createTrip(TripDto tripDto);

    List<TripDto> getAllTripsByFilter(TripFilter tripFilter);
}
