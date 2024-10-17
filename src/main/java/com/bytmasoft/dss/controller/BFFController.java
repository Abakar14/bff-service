package com.bytmasoft.dss.controller;

import com.bytmasoft.dss.dto.StudentDto;
import com.bytmasoft.dss.dto.StudentResponse;
import com.bytmasoft.dss.service.BFFService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bff")
public class BFFController {

    private final BFFService bffService;


    @GetMapping("student-data/{studentId}")
    public Mono<StudentResponse> getStudentDetails(@PathVariable Long studentId,
                                                   @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        return bffService.getStudentData(studentId, jwtToken);
    }



    @PostMapping(value = "/pictures/add", consumes = "multipart/form-data")
    ResponseEntity<StudentDto> addPictureToStudent(@Parameter(description = "Student ID") @RequestParam("studentId") Long studentId,
                                                   @Parameter(description = "Student picture")   @RequestPart("file") MultipartFile file) throws IOException{
    return null;
    }

}
