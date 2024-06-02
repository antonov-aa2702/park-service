package ru.relex.park.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.relex.park.dto.VehicleDto;
import ru.relex.park.service.VehicleService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicle")
@RestController
public class VehicleController {

    private final VehicleService vehicleService;

    /**
     * Создает новое транспортное средство. Создавать может только админ.
     *
     * @param vehicleDto транспортное средство
     * @return идентификатор созданного транспортного средства
     */
    @PostMapping
    public Integer createVehicle(@Validated
                                 @RequestBody VehicleDto vehicleDto) {
        return vehicleService.createVehicle(vehicleDto);
    }
}
