package com.nlw.certification.modules.questions.controllers;

import com.nlw.certification.modules.questions.dto.AlternativesResultDTO;
import com.nlw.certification.modules.questions.dto.QuestionResultDTO;
import com.nlw.certification.modules.questions.entities.AlternativesEntity;
import com.nlw.certification.modules.questions.entities.QuestionEntity;
import com.nlw.certification.modules.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var result = this.questionRepository.findByTechnology(technology);
        var toMap = result.stream().map(question -> mapQuestionToDTO(question))
                .collect(Collectors.toList());
        return toMap;
    }



    static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
        var questionResultDto = QuestionResultDTO.builder()
                .id(question.getId())
                .technology(question.getTechnology())
                .description(question.getDescription()).build();

        List<AlternativesResultDTO> alternativesResultDTOs = question.getAlternatives().stream()
                .map(QuestionController::mapAlternativeDTO)
                .toList();

        questionResultDto.setAlternatives(alternativesResultDTOs);
        return questionResultDto;
    }

    static AlternativesResultDTO mapAlternativeDTO(AlternativesEntity alternative) {
        return AlternativesResultDTO.builder()
                .id(alternative.getId())
                .description(alternative.getDescription()).build();
    }
}
