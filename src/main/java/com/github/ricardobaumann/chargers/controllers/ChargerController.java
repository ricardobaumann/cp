package com.github.ricardobaumann.chargers.controllers;

import com.github.ricardobaumann.chargers.dtos.ChargerDto;
import com.github.ricardobaumann.chargers.dtos.CircleDto;
import com.github.ricardobaumann.chargers.mappers.ChargerMapper;
import com.github.ricardobaumann.chargers.repos.ChargerRepo;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
public class ChargerController {

    private static final ResponseEntity<ChargerDto> NOT_FOUND = ResponseEntity.notFound().build();
    private final ChargerRepo chargerRepo;
    private final ChargerMapper chargerMapper;

    public ChargerController(ChargerRepo chargerRepo,
                             ChargerMapper chargerMapper) {
        this.chargerRepo = chargerRepo;
        this.chargerMapper = chargerMapper;
    }

    @PutMapping("/chargers/{id}")
    void put(@PathVariable String id, @RequestBody @Valid ChargerDto chargerDto) {
        chargerRepo.save(chargerMapper.toModel(id, chargerDto));
    }

    @GetMapping("/chargers/{id}")
    ResponseEntity<ChargerDto> getById(@PathVariable String id) {
        return chargerRepo.findById(id)
                .map(chargerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(NOT_FOUND);
    }

    @GetMapping("/search/zip-code/{zipCode}")
    List<ChargerDto> searchByZipCode(@PathVariable String zipCode) {
        return chargerRepo.findByZipCode(zipCode)
                .map(chargerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search/circle")
    List<ChargerDto> searchByCircle(@ModelAttribute @Valid CircleDto circleDto) {
        return chargerRepo.findByLocationNear(
                new Point(circleDto.getLatitude(), circleDto.getLongitude()),
                new Distance(circleDto.getRadiusInKm(), Metrics.KILOMETERS))
                .map(chargerMapper::toDto)
                .collect(Collectors.toList());
    }


}
