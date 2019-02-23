package com.github.ricardobaumann.chargers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Slf4j
@SpringBootApplication
public class ChargersApplication {

	private final ChargerRepo chargerRepo;
	private final MongoTemplate mongoTemplate;

	public ChargersApplication(ChargerRepo chargerRepo, MongoTemplate mongoTemplate) {
		this.chargerRepo = chargerRepo;
		this.mongoTemplate = mongoTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(ChargersApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Circle circle = new Circle(1.0, 1.0, 1.0);
			chargerRepo.save(new Charger("1","12207",new double[]{1.0, 1.0}));
			log.info("Retrieving {}", chargerRepo.findById("1"));
			log.info("Retrieving by location near {}", mongoTemplate.find(new Query(Criteria.where("location").withinSphere(circle)), Charger.class));
			log.info("Again {}", chargerRepo.findByLocationNear(new Point(1.0, 1.0), new Distance(1.0, Metrics.KILOMETERS)));
		};
	}

}
