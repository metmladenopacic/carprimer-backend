package com.metropolitan.controller;

import com.metropolitan.entity.Car;
import com.metropolitan.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cars")
@Tag(name = "Cars", description = "REST API za upravljanje automobilima")
@SecurityRequirement(name = "BearerAuth")
public class CarController {

    private final CarService service;

    @Operation(summary = "Vraća sve automobile")
    @ApiResponse(responseCode = "200", description = "Lista automobila",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Car.class))))
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Car> getAllCars() {
        return service.findAll();
    }

    @Operation(summary = "Dodaje novi automobil")
    @ApiResponse(responseCode = "201", description = "Automobil dodat")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> addCar(
            @RequestBody(
                    description = "JSON reprezentacija automobila",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Car.class)))
            @org.springframework.web.bind.annotation.RequestBody Car car) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(car));
    }

    @Operation(summary = "Vraća automobil po ID-u")
    @ApiResponse(responseCode = "200", description = "Automobil pronađen")
    @ApiResponse(responseCode = "404", description = "Automobil nije pronađen")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> getCarById(@Parameter(description = "ID automobila") @PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Briše automobil po ID-u")
    @ApiResponse(responseCode = "204", description = "Automobil obrisan")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@Parameter(description = "ID automobila") @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
