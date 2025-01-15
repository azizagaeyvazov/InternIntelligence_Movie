package com.intern_intelligence.movie.controller;

import com.intern_intelligence.movie.dto.AuthenticationResponse;
import com.intern_intelligence.movie.dto.LoginRequest;
import com.intern_intelligence.movie.dto.UpdatePasswordRequest;
import com.intern_intelligence.movie.dto.UserRegisterRequest;
import com.intern_intelligence.movie.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        authenticationService.registerForUser(userRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify-register")
    public ResponseEntity<Void> verifyRegisterUser(@RequestParam String token){
        authenticationService.verifyRegisterForUser(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.loginUser(loginRequest));
    }

    @PostMapping("/new-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        authenticationService.generateResetToken(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestParam String token, @Valid @RequestBody UpdatePasswordRequest request) {
        authenticationService.updatePassword(token, request.getNewPassword());
        return ResponseEntity.ok().build();
    }
}













