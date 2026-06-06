package com.metropolitan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String jwtToken;

    @BeforeEach
    void loginAndGetToken() throws Exception {
        String loginJson = """
                {
                    "usernameOrEmail": "admin",
                    "password": "admin"
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        jwtToken = new ObjectMapper()
                .readTree(result.getResponse().getContentAsString())
                .get("accessToken").asText();
    }

    @Test
    void testGetAllCars() throws Exception {
        mockMvc.perform(get("/api/cars")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    @Test
    void testGetCarById() throws Exception {
        mockMvc.perform(get("/api/cars/{id}", 1)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testAddCar() throws Exception {
        String newCarJson = """
                {
                    "brand": "Mazda",
                    "model": "3",
                    "manufactureYear": 2022
                }
                """;

        mockMvc.perform(post("/api/cars")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCarJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand").value("Mazda"));
    }

    @Test
    void testDeleteCar() throws Exception {
        // Prvo kreiramo auto
        String newCarJson = """
                {
                    "brand": "Opel",
                    "model": "Astra",
                    "manufactureYear": 2020
                }
                """;

        MvcResult createResult = mockMvc.perform(post("/api/cars")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCarJson))
                .andExpect(status().isCreated())
                .andReturn();

        Long createdCarId = new ObjectMapper()
                .readTree(createResult.getResponse().getContentAsString())
                .get("id").asLong();

        // Brišemo auto
        mockMvc.perform(delete("/api/cars/{id}", createdCarId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }
}
