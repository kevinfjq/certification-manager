package com.nlw.certification.modules.students.useCases;

import com.nlw.certification.modules.questions.entities.AlternativesEntity;
import com.nlw.certification.modules.questions.entities.QuestionEntity;
import com.nlw.certification.modules.questions.repositories.QuestionRepository;
import com.nlw.certification.modules.students.dto.StudentCertificationAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCertificationAnswersUseCase {
//    @Autowired
//    private StudentRepository studentRepository;
    @Autowired
    private QuestionRepository questionRepository;
    public StudentCertificationAnswerDTO execute(StudentCertificationAnswerDTO studentCertificationAnswerDTO){
//        var student = studentRepository.findByEmail(studentCertificationAnswerDTO.getEmail());
//
//        if (student.isEmpty()) {
//            throw new Exception("Invalid student email");
//        }

        List<QuestionEntity> questionEntities =  questionRepository.findByTechnology(studentCertificationAnswerDTO.getTechnology());

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
                });
        return studentCertificationAnswerDTO;
    }
}
