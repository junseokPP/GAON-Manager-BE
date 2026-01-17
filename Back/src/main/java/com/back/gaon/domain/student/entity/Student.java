package com.back.gaon.domain.student.entity;

import com.back.gaon.common.baseEntity.BaseEntity;
import com.back.gaon.domain.student.enums.StudentStatus;
import com.back.gaon.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Student extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_user_id")
    private User parent;

    private String school;

    private String grade;

    @Enumerated(EnumType.STRING)
    private StudentStatus status;

    public Student(User user, User parent, String school, String grade) {
        this.user = user;
        this.parent = parent;
        this.school = school;
        this.grade = grade;
        this.status = StudentStatus.ACTIVE;
    }

    public void pause() {
        this.status = StudentStatus.PAUSED;
    }

    public void drop() {
        this.status = StudentStatus.DROPPED;
    }
}