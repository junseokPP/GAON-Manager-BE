package com.back.gaon.domain.auth.controller;

import com.back.gaon.domain.auth.dto.request.LoginRequest;
import com.back.gaon.domain.auth.dto.response.LoginResponse;
import com.back.gaon.domain.auth.service.AuthService;
import com.back.gaon.domain.auth.service.AuthTokens;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response // 쿠키다룰려고 사용함.
    ){
        AuthTokens tokens = authService.login(request);

        ResponseCookie accessCookie = ResponseCookie.from("access_token",tokens.accessToken())
                    .httpOnly(true)
                    .secure(false) // TODO : 배포시 true로 바꿔야함
                    .maxAge(tokens.accessTokenMaxAgeSeconds())
                    .sameSite("Strict")
                    .path("/")
                    .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokens.refreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("Strict")
                    .maxAge(tokens.refreshTokenMaxAgeSeconds())
                    .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        LoginResponse body = new LoginResponse(
                tokens.userId(),
                tokens.role(),
                tokens.name()
        );

        return ResponseEntity.ok(body);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(401).build();
        }

        AuthTokens newTokens = authService.refreshAccessToken(refreshToken);

        ResponseCookie newAccessCookie = ResponseCookie.from("access_token", newTokens.accessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(newTokens.accessTokenMaxAgeSeconds())
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, newAccessCookie.toString());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        ResponseCookie deleteAccess = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(0)
                .build();

        ResponseCookie deleteRefresh = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteAccess.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, deleteRefresh.toString());

        return ResponseEntity.noContent().build();
    }
}
