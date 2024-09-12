package com.bytmasoft.controller;

import com.bytmasoft.entity.StudentDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BFFControllerImpl implements BFFControllerApi {

    @Override
    public ResponseEntity<StudentDetails> getStudentDetails(Long studentId) {
        return null;
    }


}
