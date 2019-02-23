package com.github.ricardobaumann.chargers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargerDto {
    private String zipCode;
    private Double latitude;
    private Double longitude;
}
