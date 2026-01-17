package com.back.gaon.domain.student.repository;

import com.back.gaon.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByUserId(Long userId);
}