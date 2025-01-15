package com.intern_intelligence.movie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intern_intelligence.movie.dto.AuthenticationResponse;
import com.intern_intelligence.movie.dto.LoginRequest;
import com.intern_intelligence.movie.dto.UserRegisterRequest;
import com.intern_intelligence.movie.exception.InvalidTokenException;
import com.intern_intelligence.movie.exception.UserAlreadyExistException;
import com.intern_intelligence.movie.exception.TokenNotFoundException;
import com.intern_intelligence.movie.exception.UserNotFoundException;
import com.intern_intelligence.movie.mapper.UserMapper;
import com.intern_intelligence.movie.model.UUIDToken;
import com.intern_intelligence.movie.model.User;
import com.intern_intelligence.movie.repository.UUIDTokenRepository;
import com.intern_intelligence.movie.repository.UserRepository;
import com.intern_intelligence.movie.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmailService emailService;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UUIDTokenRepository UUIDTokenRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public void registerForUser(UserRegisterRequest request) {

        var userEntityOpt = userRepository.findByEmail(request.getEmail());

        if (userEntityOpt.isEmpty()) {
            var user = userMapper.dtoToEntity(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            var uuidToken = generateUUIDTokenForUserRegister(user);
            userRepository.save(user);
            UUIDTokenRepository.save(uuidToken);
            emailService.sendRegistrationLink(user, uuidToken.getToken());
            return;
        }

        var user = userEntityOpt.get();

        if (user.isEnabled()) {
            throw new UserAlreadyExistException();
        }

        var uuidTokenOpt = UUIDTokenRepository.findByUserId(user.getId());

        if (uuidTokenOpt.isEmpty()) {
            userMapper.updateUserEntity(request, user);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            var uuidToken = generateUUIDTokenForUserRegister(user);
            userRepository.save(user);
            UUIDTokenRepository.save(uuidToken);
            emailService.sendRegistrationLink(user, uuidToken.getToken());
            return;
        }

        var uuidToken = uuidTokenOpt.get();
        if (uuidToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            emailService.sendRegistrationLink(user, uuidToken.getToken());
        } else {
            userMapper.updateUserEntity(request, user);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            updateUUIDToken(uuidToken);
            userRepository.save(user);
            UUIDTokenRepository.save(uuidToken);
            emailService.sendRegistrationLink(user, uuidToken.getToken());
        }
    }

    public void verifyRegisterForUser(String token) {

        UUIDToken uuidToken = UUIDTokenRepository.findByToken(token)
                .orElseThrow(TokenNotFoundException::new);

        if (uuidToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException();
        }
        var user = uuidToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        UUIDTokenRepository.delete(uuidToken);
    }

    public AuthenticationResponse loginUser(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void generateResetToken(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(
                UserNotFoundException::new);

        UUIDTokenRepository.deleteUUIDTokenByUserId(user.getId());

        String token = UUID.randomUUID().toString();
        var passToken = UUIDToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(30))// Token expires in 30 minutes
                .build();

        UUIDTokenRepository.save(passToken);
        emailService.sendForgotPasswordLink(user, token);
    }

    public void updatePassword(String token, String newPassword) {

        UUIDToken uuidToken = UUIDTokenRepository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);

        if (uuidToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException();
        }

        var user = uuidToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String refreshToken;
        final String email;
        final String tokenType;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        email = jwtService.extractEmail(refreshToken);
        tokenType = jwtService.extractTokenType(refreshToken);

        if (email != null) {
            var userDetails = this.userRepository.findByEmail(email).orElseThrow(
                    UserNotFoundException::new);

            if ("REFRESH".equals(tokenType) && jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateAccessToken(userDetails);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
            response.setStatus(BAD_REQUEST.value());
        }
    }

    private UUIDToken generateUUIDTokenForUserRegister(User user) {
        String token = UUID.randomUUID().toString();
        return UUIDToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(24))
                .build();
    }

    private void updateUUIDToken(UUIDToken uuidToken) {
        String token = UUID.randomUUID().toString();
        uuidToken.setToken(token);
        uuidToken.setExpiryDate(LocalDateTime.now().plusHours(24));
    }
}
