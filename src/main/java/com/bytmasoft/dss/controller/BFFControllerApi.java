package com.bytmasoft.dss.controller;

import com.bytmasoft.dss.entity.StudentDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;



public interface BFFControllerApi {

    @GetMapping("student-details/{studentId}")
    public ResponseEntity<StudentDetails> getStudentDetails(@PathVariable Long studentId,
                                                            @RequestHeader (HttpHeaders.AUTHORIZATION) String authorizationHeader);



}
