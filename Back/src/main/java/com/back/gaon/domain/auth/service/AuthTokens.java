package com.back.gaon.domain.auth.service;

public record AuthTokens(
        String accessToken,
        long accessTokenMaxAgeSeconds,
        String refreshToken,
        long refreshTokenMaxAgeSeconds,
        Long userId,
        String role,
        String name
) {}