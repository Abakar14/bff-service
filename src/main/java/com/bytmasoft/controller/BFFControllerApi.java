package com.bytmasoft.controller;

import com.bytmasoft.entity.StudentDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BFFControllerApi {

    @GetMapping("student-details/{student_id}")
    public ResponseEntity<StudentDetails> getStudentDetails(@PathVariable("student_id") Long studentId);


}
