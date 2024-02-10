package com.nlw.certification.modules.students.useCases;

import com.nlw.certification.modules.students.dto.VerifyHasCertificationDTO;
import com.nlw.certification.modules.students.repositories.CertificationStudentRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationService {

    @Autowired
    private CertificationStudentRepostory certificationStudentRepostory;
    public boolean execute(VerifyHasCertificationDTO dto){
        var result = this.certificationStudentRepostory.findByStudentEmailAndTechnology(dto.getEmail(), dto.getTechnology());
        if(!result.isEmpty()) {
            return true;
        }
        return false;
    }

}
