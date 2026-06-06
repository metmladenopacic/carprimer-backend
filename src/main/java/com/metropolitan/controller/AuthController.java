package com.metropolitan.controller;

import com.metropolitan.dto.JWTAuthResponse;
import com.metropolitan.dto.LoginDto;
import com.metropolitan.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    // Marker da potvrdimo koja verzija koda se ZAISTA vrti na Render-u.
    // Ako ovo vrati 200 + tekst -> deploy-ovan je trenutni kod (i login mora da radi).
    // Ako vrati 403/404 -> Render i dalje vrti STARI image.
    @GetMapping("/version")
    public ResponseEntity<String> version() {
        return ResponseEntity.ok("BUILD-MARKER-2026-06-07-A");
    }

    // Build Login REST API
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }
}
