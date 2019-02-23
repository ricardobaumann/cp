package com.github.ricardobaumann.chargers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chargers")
public class Charger {

    @Id
    private String id;
    private String zipCode;

    @GeoSpatialIndexed(name="location")
    private double[] location;
}
