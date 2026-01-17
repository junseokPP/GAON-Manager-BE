package com.back.gaon.domain.auth.service;

import com.back.gaon.domain.auth.dto.request.LoginRequest;
import com.back.gaon.domain.auth.jwt.JwtProvider;
import com.back.gaon.domain.user.entity.User;
import com.back.gaon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthTokens login(LoginRequest request) {

        User user = userRepository.findByLoginId((request.loginId()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 오류"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호 오류");
        }

        String accessToken = jwtProvider.createAccessToken(user);
        String refreshToken = jwtProvider.createRefreshToken(user);

        return new AuthTokens(
                accessToken,
                jwtProvider.getAccessTokenTtlSeconds(),
                refreshToken,
                jwtProvider.getRefreshTokenTtlSeconds(),
                user.getId(),
                user.getRole().name(),
                user.getName()
        );
    }

    public AuthTokens refreshAccessToken(String refreshToken) {


        Long userId = jwtProvider.validateAndExtractUserId(refreshToken);


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        String newAccessToken = jwtProvider.createAccessToken(user);

        return new AuthTokens(
                newAccessToken,
                jwtProvider.getAccessTokenTtlSeconds(),
                null, // refresh는 재발급 안 함
                0,
                user.getId(),
                user.getRole().name(),
                user.getName()
        );
    }
}
