package com.github.ricardobaumann.chargers.repos;

import com.github.ricardobaumann.chargers.models.Charger;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface ChargerRepo extends MongoRepository<Charger, String> {
    Stream<Charger> findByLocationNear(Point p, Distance d);

    Stream<Charger> findByZipCode(String zipCode);
}
