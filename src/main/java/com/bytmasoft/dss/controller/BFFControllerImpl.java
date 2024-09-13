package com.bytmasoft.dss.controller;

import com.bytmasoft.dss.entity.StudentDetails;
import com.bytmasoft.dss.service.BFFServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bff")
public class BFFControllerImpl implements BFFControllerApi {

    private final BFFServiceImpl bffService;

    @Override
    public ResponseEntity<StudentDetails> getStudentDetails(Long studentId, String authorizationHeader) {
        return ResponseEntity.ok(bffService.getStudentDetails(studentId, authorizationHeader));
    }
}
