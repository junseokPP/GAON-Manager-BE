package com.back.gaon.global.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 공통 응답 포맷
    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message, Object detail) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        if (detail != null) body.put("detail", detail);
        return ResponseEntity.status(status).body(body);
    }

    // 1) @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));
        return build(HttpStatus.BAD_REQUEST, "요청 값 검증에 실패했습니다.", fieldErrors);
    }

    // 2) 조회 대상 없음 (서비스에서 throw new EntityNotFoundException(...))
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    // 3) 비즈니스 논리 오류(중복, 상태 불일치 등)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegal(IllegalArgumentException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    // 3-1) 상태가 맞지 않을 때 (승인/반려 조건 위반 등)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    // 4) DB 제약 위반 (unique, FK 등) — 혹시 서비스에서 못 막고 내려올 때
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        return build(HttpStatus.CONFLICT, "데이터 무결성 제약 조건 위반", null);
    }

    // 5) 예상 못한 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleEtc(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.", null);
    }
}