package com.github.ricardobaumann.chargers;

import com.github.ricardobaumann.chargers.dtos.ChargerDto;
import com.github.ricardobaumann.chargers.mappers.ChargerMapper;
import com.github.ricardobaumann.chargers.services.ChargerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChargerController {

    private static final ResponseEntity<ChargerDto> NOT_FOUND = ResponseEntity.notFound().build();
    private final ChargerService chargerService;
    private final ChargerMapper chargerMapper;

    public ChargerController(ChargerService chargerService,
                             ChargerMapper chargerMapper) {
        this.chargerService = chargerService;
        this.chargerMapper = chargerMapper;
    }

    @PutMapping("/chargers/{id}")
    void post(@PathVariable String id, ChargerDto chargerDto) {
        chargerService.create(chargerMapper.toModel(id,chargerDto));
    }

    @GetMapping("/chargers/{id}")
    ResponseEntity<ChargerDto> getById(@PathVariable String id) {
        return chargerService.getById(id)
                .map(chargerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(NOT_FOUND);
    }


}
