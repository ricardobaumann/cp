package com.github.ricardobaumann.chargers.repos;

import com.github.ricardobaumann.chargers.models.Charger;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChargerRepoTest {

    @Autowired
    private ChargerRepo chargerRepo;

    private Charger market;
    private Charger home;

    @Before
    public void setUp() {
        chargerRepo.deleteAll();
        chargerRepo.save(market = new Charger("1","market",new double[]{52.4277,13.3135467}));
        chargerRepo.save(home = new Charger("2","home",new double[]{52.4258869,13.3193983}));
        chargerRepo.save(new Charger("3","far away",new double[]{52.4050461,13.0387207}));
    }

    @Test
    public void shouldFindInCircle() {
        //When
        List<Charger> results = chargerRepo.findByLocationNear(new Point(52.4258870, 13.3193983), new Distance(2.0, Metrics.KILOMETERS)).collect(Collectors.toList());

        //Then
        assertThat(results)
                .containsExactlyInAnyOrder(market, home);
    }

    @Test
    public void shouldFindInZipCode() {
        //Given
        Charger closeToHome = new Charger("9","home",new double[]{52.4258869,13.3193983});
        chargerRepo.save(closeToHome);
        //When
        List<Charger> results = chargerRepo.findByZipCode(home.getZipCode()).collect(Collectors.toList());
        //Then
        assertThat(results).containsExactlyInAnyOrder(home, closeToHome);
    }

    @Test
    public void shouldFindById() {
        // When
        Optional<Charger> result = chargerRepo.findById("1");
        //Then
        assertThat(result).contains(market);
    }

    @Test
    public void shouldReturnEmptyOnNonExistentId() {
        // When
        Optional<Charger> result = chargerRepo.findById("99");
        //Then
        assertThat(result).isEmpty();
    }

}