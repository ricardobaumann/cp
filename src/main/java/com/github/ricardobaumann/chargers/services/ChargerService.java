package com.github.ricardobaumann.chargers.services;

import com.github.ricardobaumann.chargers.Charger;
import com.github.ricardobaumann.chargers.ChargerRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChargerService {

    private final ChargerRepo chargerRepo;

    public ChargerService(ChargerRepo chargerRepo) {
        this.chargerRepo = chargerRepo;
    }

    public Charger create(Charger charger) {
        return chargerRepo.save(charger);
    }

    public Optional<Charger> getById(String id) {
        return chargerRepo.findById(id);
    }
}
