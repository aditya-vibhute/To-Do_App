package com.example.To_do_App.controller;
    
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.To_do_App.dto.request.LoginRequest;
import com.example.To_do_App.dto.request.SignupRequest;
import com.example.To_do_App.dto.response.AuthResponse;
import com.example.To_do_App.security.JwtService;
import com.example.To_do_App.service.AuthService;
import com.example.To_do_App.service.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;
    private final ReportService reportService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            AuthService authService,
            JwtService jwtService,
            ReportService reportService,
            AuthenticationManager authenticationManager) {

        this.authService = authService;
        this.jwtService = jwtService;
        this.reportService = reportService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignupRequest req) {
        authService.signup(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        Authentication authentication =
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );

        // ✅ ALWAYS SAFE
        String email = authentication.getName();

        // 🔥 Auto-generate report for last active day
        reportService.generateLastActiveDayReport(email);

        String token = jwtService.generateToken(email);

        return new AuthResponse(token);
    }
}
