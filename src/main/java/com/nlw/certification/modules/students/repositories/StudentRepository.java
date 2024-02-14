package com.nlw.certification.modules.students.repositories;

import com.nlw.certification.modules.students.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    Optional<StudentEntity> findByEmail(String email);
}
