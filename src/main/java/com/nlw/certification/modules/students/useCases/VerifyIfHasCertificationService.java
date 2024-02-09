package com.nlw.certification.modules.students.useCases;

import com.nlw.certification.modules.students.dto.VerifyHasCertificationDTO;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationService {
    public boolean execute(VerifyHasCertificationDTO dto){
        return dto.getEmail().equals("kevifjq@gmail.com") && dto.getTechnology().equalsIgnoreCase("java");
    }
}
