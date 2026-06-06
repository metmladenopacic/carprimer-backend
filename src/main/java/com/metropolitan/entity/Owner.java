package com.metropolitan.entity;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entitet koji predstavlja vlasnika automobila")
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID vlasnika", example = "1")
    private Long id;

    @Schema(description = "Ime vlasnika", example = "Ana")
    @Column(name = "first_name")
    private String firstName;

    @Schema(description = "Prezime vlasnika", example = "Petrović")
    @Column(name = "last_name")
    private String lastName;

}
