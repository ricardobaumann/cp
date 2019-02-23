package com.github.ricardobaumann.chargers;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargerRepo extends MongoRepository<Charger, String> {
    List<Charger> findByLocationNear(Point p, Distance d);
}
