package com.bytmasoft.dss.controller;

import com.bytmasoft.dss.dto.StudentDetailCreateDTO;
import com.bytmasoft.dss.dto.StudentDetailDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface BFFControllerApi {

    @GetMapping("student-details/{studentId}")
    public ResponseEntity<StudentDetailDTO> getStudentDetails(@PathVariable Long studentId,
                                                            @RequestHeader (HttpHeaders.AUTHORIZATION) String authorizationHeader);


    @PostMapping(value = "/pictures", consumes = "multipart/form-data")
    ResponseEntity<StudentDetailDTO> addStudentWithPicture(
            @RequestPart(value = "studentCreateDto") @Parameter(schema =@Schema(type = "string", format = "binary")) final StudentDetailCreateDTO studentCreateDto,
            @Parameter(description = "Student picture")  @RequestPart(value = "picture") MultipartFile picture) throws IOException;

    @PostMapping(value = "/pictures/add", consumes = "multipart/form-data")
    ResponseEntity<StudentDetailDTO> addPictureToStudent(@Parameter(description = "Student ID") @RequestParam("studentId") Long studentId,
                                                   @Parameter(description = "Student picture")   @RequestPart("file") MultipartFile file) throws IOException;


}
