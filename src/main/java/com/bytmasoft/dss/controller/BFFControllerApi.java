package com.bytmasoft.dss.controller;

import com.bytmasoft.dss.dto.StudentDetailDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;



public interface BFFControllerApi {

    @GetMapping("student-details/{studentId}")
    public ResponseEntity<StudentDetailDTO> getStudentDetails(@PathVariable Long studentId,
                                                            @RequestHeader (HttpHeaders.AUTHORIZATION) String authorizationHeader);

}
