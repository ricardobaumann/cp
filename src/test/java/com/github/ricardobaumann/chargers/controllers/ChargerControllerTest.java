package com.github.ricardobaumann.chargers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ricardobaumann.chargers.dtos.ChargerDto;
import com.github.ricardobaumann.chargers.mappers.ChargerMapper;
import com.github.ricardobaumann.chargers.models.Charger;
import com.github.ricardobaumann.chargers.repos.ChargerRepo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChargerControllerTest {

    private ChargerRepo chargerRepo = mock(ChargerRepo.class);

    private ChargerController chargerController =
            new ChargerController(chargerRepo, new ChargerMapper());

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(chargerController).build();
    }

    @Test
    public void shouldReturnOkOnSuccessPost() throws Exception {
        //Given
        Charger charger = new Charger("1", "123", new double[]{1.0, 2.0});
        when(chargerRepo.save(charger)).thenReturn(charger);
        //When
        mockMvc.perform(put("/chargers/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(
                        new ChargerDto("123", 1.0, 2.0))))
                .andExpect(status().isOk());

        //Then
        verify(chargerRepo).save(charger);
    }

    @Test
    public void shouldReturnBadRequestOnPostValidationFailure() throws Exception {
        //When
        mockMvc.perform(put("/chargers/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(
                        new ChargerDto())))
                .andExpect(status().isBadRequest());

        //Then
        verify(chargerRepo, never()).save(any());
    }

    @Test
    public void shouldReturnExistentChargerById() throws Exception {
        //Given
        Charger charger = new Charger("1", "123", new double[]{1.0, 2.0});
        when(chargerRepo.findById("1")).thenReturn(Optional.of(charger));
        //When
        mockMvc.perform(get("/chargers/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zipCode", is("123")))
                .andExpect(jsonPath("$.latitude", is(1.0)))
                .andExpect(jsonPath("$.longitude", is(2.0)));
        //Then
        verify(chargerRepo).findById("1");
    }

    @Test
    public void shouldReturnNotFoundOnAbsentId() throws Exception {
        //Given
        when(chargerRepo.findById("1")).thenReturn(Optional.empty());
        //When
        mockMvc.perform(get("/chargers/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        //Then
        verify(chargerRepo).findById("1");
    }

    @Test
    public void shouldReturnExistentChargersByZipCode() throws Exception {
        //Given
        Charger charger = new Charger("1", "123", new double[]{1.0, 2.0});
        when(chargerRepo.findByZipCode("123")).thenReturn(Stream.of(charger));
        //When
        mockMvc.perform(get("/search/zip-code/{zipCode}", "123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].zipCode", is("123")))
                .andExpect(jsonPath("$[0].latitude", is(1.0)))
                .andExpect(jsonPath("$[0].longitude", is(2.0)));
        //Then
        verify(chargerRepo).findByZipCode("123");
    }

    @Test
    public void shouldReturnExistentChargersByCircle() throws Exception {
        //Given
        Charger charger = new Charger("1", "123", new double[]{1.0, 2.0});
        when(chargerRepo.findByLocationNear(new Point(1.0, 2.0), new Distance(1, Metrics.KILOMETERS)))
                .thenReturn(Stream.of(charger));
        //When
        mockMvc.perform(get("/search/circle")
                .param("latitude", "1.0")
                .param("longitude", "2.0")
                .param("radiusInKm", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].zipCode", is("123")))
                .andExpect(jsonPath("$[0].latitude", is(1.0)))
                .andExpect(jsonPath("$[0].longitude", is(2.0)));
        //Then
        verify(chargerRepo).findByLocationNear(new Point(1.0, 2.0), new Distance(1, Metrics.KILOMETERS));
    }
}