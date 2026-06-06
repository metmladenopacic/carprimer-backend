package com.metropolitan.controller;

import com.metropolitan.entity.Owner;
import com.metropolitan.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/owners")
@Tag(name = "Owner API", description = "REST operacije za entitet Owner")
public class OwnerController {

    private final OwnerService ownerService;


    @GetMapping
    @Operation(summary = "Dohvatanje svih vlasnika")
    public List<Owner> getAllOwners() {
        return ownerService.findAll();
    }
}
