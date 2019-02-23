package com.github.ricardobaumann.chargers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChargerController {

    private final ChargerRepo chargerRepo;

    public ChargerController(ChargerRepo chargerRepo) {
        this.chargerRepo = chargerRepo;
    }
}
