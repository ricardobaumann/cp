package com.github.ricardobaumann.chargers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargerDto {
    @NotNull
    private String zipCode;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
