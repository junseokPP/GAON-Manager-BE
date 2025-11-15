package com.back.gaon.domain.schedule.template.enums;

/**
 * 버전 상태
 * - DRAFT: 편집 중(임시저장)
 * - PENDING: 제출(승인 대기)
 * - APPROVED: 승인(운영 반영)
 * - REJECTED: 반려
 */
public enum TemplateStatus {
    DRAFT,
    PENDING,
    APPROVED,
    REJECTED
}