package com.github.ricardobaumann.chargers.mappers;

import com.github.ricardobaumann.chargers.Charger;
import com.github.ricardobaumann.chargers.dtos.ChargerDto;
import org.springframework.stereotype.Component;

@Component
public class ChargerMapper {
    public Charger toModel(String id,ChargerDto chargerDto) {
        return new Charger(id,
                chargerDto.getZipCode(),
                new double[]{chargerDto.getLatitude(), chargerDto.getLongitude()});
    }

    public ChargerDto toDto(Charger charger) {
        return new ChargerDto(charger.getZipCode(), charger.getLocation()[0], charger.getLocation()[1]);
    }
}
