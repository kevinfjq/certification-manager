package com.nlw.certification.modules.students.useCases;

import com.nlw.certification.modules.questions.entities.AlternativesEntity;
import com.nlw.certification.modules.questions.entities.QuestionEntity;
import com.nlw.certification.modules.questions.repositories.QuestionRepository;
import com.nlw.certification.modules.students.dto.StudentCertificationAnswerDTO;
import com.nlw.certification.modules.students.entities.AnswersCertificationEntity;
import com.nlw.certification.modules.students.entities.CertificationStudentEntity;
import com.nlw.certification.modules.students.entities.StudentEntity;
import com.nlw.certification.modules.students.repositories.CertificationStudentRepository;
import com.nlw.certification.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    public CertificationStudentEntity execute(StudentCertificationAnswerDTO studentCertificationAnswerDTO){


        List<QuestionEntity> questionEntities =  questionRepository.findByTechnology(studentCertificationAnswerDTO.getTechnology());


        List<AnswersCertificationEntity> answersCertifications = new ArrayList<>();

        studentCertificationAnswerDTO.getQuestionAnswer().stream()
                .forEach(questionAnswer -> {

                    var questionEntity = questionEntities.stream()
                            .filter(question -> question.getId()
                            .equals(questionAnswer.getQuestionID()))
                            .findFirst().get();

                    var findCorrectAlternative = questionEntity.getAlternatives().stream()
                            .filter(AlternativesEntity::isCorrect)
                            .findFirst().get();

                    questionAnswer.setCorrect(findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID()));


                    var answerCertificationEntity = AnswersCertificationEntity.builder()
                            .answerID(questionAnswer.getAlternativeID())
                            .questionID(questionAnswer.getQuestionID())
                            .isCorrect(questionAnswer.isCorrect())
                            .build();

                    answersCertifications.add(answerCertificationEntity);
                });

        var student = studentRepository.findByEmail(studentCertificationAnswerDTO.getEmail());
        UUID studentID;
        if(student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(studentCertificationAnswerDTO.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        } else  {
            studentID = student.get().getId();
        }
        int correctAnswers =(int) answersCertifications.stream().filter(AnswersCertificationEntity::isCorrect).count();


        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                .technology(studentCertificationAnswerDTO.getTechnology())
                .studentID(studentID)
                .grade(correctAnswers)
                .build();

        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answerCertification -> {
            answerCertification.setCertificationID(certificationStudentEntity.getId());
            answerCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationEntities(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);

        return certificationStudentCreated;
    }
}
