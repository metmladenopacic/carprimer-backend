package com.metropolitan.entity;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entitet koji predstavlja vozilo")
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID automobila", example = "1")
    private Long id;

    @Schema(description = "Marka automobila", example = "Toyota")
    private String brand;

    @Schema(description = "Model automobila", example = "Corolla")
    private String model;

    @Schema(description = "Godina proizvodnje", example = "2023")
    @Column(name = "manufacture_year")
    private int manufactureYear;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @Schema(description = "Vlasnik automobila")
    private Owner owner;
}
