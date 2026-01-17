package com.back.gaon.domain.auth.dto.response;

public record LoginResponse(
        Long userId,
        String role,
        String name
) {}