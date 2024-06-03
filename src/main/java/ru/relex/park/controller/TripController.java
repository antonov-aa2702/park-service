package ru.relex.park.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.relex.park.dto.TripDto;
import ru.relex.park.dto.filter.TripFilter;
import ru.relex.park.service.TripService;

import java.util.List;

@RequestMapping("/api/v1/trips")
@RestController
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    /**
     * Осуществляет создание поездки. Создавать может только пользователь
     * с ролью "DRIVER". Нельзя создавать поездки, если транспортное средство
     * занято.
     *
     * @param tripDto информация о поездке
     * @return идентификатор созданной поездки
     */
    @PostMapping
    public Integer createTrip(@Validated @RequestBody TripDto tripDto) {
        return tripService.createTrip(tripDto);
    }

    /**
     * Возвращает список всех поездок по фильтру. В качестве параметров фильтрации
     * задается объект {@link TripFilter}. Нельзя смотреть информацию о чужих поездках.
     *
     * @param tripFilter информация о поездке
     * @return список поездок
     */
    @GetMapping("/filter")
    public List<TripDto> getAllTripsByFilter(@Validated @RequestBody TripFilter tripFilter) {
        return tripService.getAllTripsByFilter(tripFilter);
    }
}
