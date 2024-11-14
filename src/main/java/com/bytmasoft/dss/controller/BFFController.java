package com.bytmasoft.dss.controller;

import com.bytmasoft.dss.dto.StudentDetailsDto;
import com.bytmasoft.dss.dto.StudentDto;
import com.bytmasoft.dss.dto.StudentResponse;
import com.bytmasoft.dss.enums.DocumentType;
import com.bytmasoft.dss.service.BFFService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/bff", produces = MediaType.APPLICATION_JSON_VALUE)
public class BFFController {

private final BFFService bffService;


@PostMapping(value = "/student-details", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
public Mono<StudentDetailsDto> addStudentDetails(@RequestPart("studentDetails") com.bytmasoft.dss.dto.StudentDetailsCreateDto studentDetailsDto,
                                                 @RequestPart("files") List<MultipartFile> files,
                                                 @RequestParam("documentTypes") List<DocumentType> documentTypes,
                                                 @RequestHeader("Authorization") String jwtToken) {

	return bffService.addStudentDetailsWithFiles(studentDetailsDto, files, documentTypes, jwtToken);

}


@GetMapping("student-details/{studentId}")
public Mono<StudentResponse> getStudentDetails(@PathVariable Long studentId,
                                               @RequestHeader("Authorization") String jwtToken) {

	return bffService.getStudentDetails(studentId, jwtToken);
}

@GetMapping("student/{studentId}")
public Mono<StudentDto> getStudent(@PathVariable Long studentId,
                                   @RequestHeader("Authorization") String jwtToken) {

	return bffService.getStudent(studentId, jwtToken);
}


@PostMapping(value = "/pictures/add", consumes = "multipart/form-data")
ResponseEntity<StudentDto> addPictureToStudent(@Parameter(description = "Student ID") @RequestParam("studentId") Long studentId,
                                               @Parameter(description = "Student picture") @RequestPart("file") MultipartFile file) throws IOException {
	return null;
}

}
