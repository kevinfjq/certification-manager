package com.nlw.certification.modules.students.controllers;

import com.nlw.certification.modules.students.dto.VerifyHasCertificationDTO;
import com.nlw.certification.modules.students.useCases.VerifyIfHasCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private VerifyIfHasCertificationService verifyIfHasCertificationService;
    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        var result = this.verifyIfHasCertificationService.execute(verifyHasCertificationDTO);
        if(result) return "Usuário ja fez a prova";
        return "Usuario pode fazer a prova";
    }
}