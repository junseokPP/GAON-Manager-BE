package com.back.gaon.domain.auth.dto.request;

public record LoginRequest(
    String loginId,
    String password
) {
}
