package com.github.ricardobaumann.chargers;

import com.github.ricardobaumann.chargers.models.Charger;
import com.github.ricardobaumann.chargers.repos.ChargerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class ChargersApplication {

	private final ChargerRepo chargerRepo;

	public ChargersApplication(ChargerRepo chargerRepo) {
		this.chargerRepo = chargerRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(ChargersApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			chargerRepo.save(new Charger("1","12207",new double[]{47.3700, 8.5310}));
			log.info("Retrieving {}", chargerRepo.findById("1"));
			log.info("Searching {}", chargerRepo.findByLocationNear(new Point(47.3700, 8.5310), new Distance(2.0, Metrics.KILOMETERS)).collect(Collectors.toList()));
		};
	}

}
