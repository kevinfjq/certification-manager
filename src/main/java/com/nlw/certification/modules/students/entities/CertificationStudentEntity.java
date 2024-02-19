package com.nlw.certification.modules.students.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "certifications")
public class CertificationStudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "student_id")
    private UUID studentID;

    @Column(length = 100)
    private String technology;
    @Column(length = 10)
    private int grade;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private StudentEntity studentEntity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_certification_id", insertable = false, updatable = false)
    @JsonManagedReference
    private List<AnswersCertificationEntity> answersCertificationEntities;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
