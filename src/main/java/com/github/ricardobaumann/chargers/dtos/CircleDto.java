package com.github.ricardobaumann.chargers.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CircleDto {
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private double radiusInKm;
}
