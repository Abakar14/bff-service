package com.bytmasoft.dss.controller;

import com.bytmasoft.dss.dto.StudentDetailsCreateDto;
import com.bytmasoft.dss.dto.StudentDetailsDto;
import com.bytmasoft.dss.enums.DocumentType;
import com.bytmasoft.dss.service.BFFService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/bff", produces = MediaType.APPLICATION_JSON_VALUE)
public class BFFController {

private static final Logger logger = LoggerFactory.getLogger(BFFController.class);

private final BFFService bffService;

@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@PostMapping(value = "/student-details", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
public StudentDetailsDto addStudentDetails(@RequestPart("studentDetailsCreateDto") StudentDetailsCreateDto studentDetailsCreateDto,
                                           @RequestPart("files") List<MultipartFile> files,
                                           @RequestParam("documentTypes") List<DocumentType> documentTypes,
                                           @RequestHeader("Authorization") String jwtToken) {

	logger.debug("Received StudentDetailsCreateDto: {}", studentDetailsCreateDto);
	logger.debug("Received Files: {}", files.size());
	logger.debug("Received Document Types: {}", documentTypes);

	return bffService.addStudentDetailsWithFiles(studentDetailsCreateDto, files, documentTypes, jwtToken)
			       .switchIfEmpty(Mono.error(new IllegalStateException("No response generated"))).block();
}

@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public StudentDetailsDto addStudentDetails(
		@RequestPart("studentDetailsCreateDto") StudentDetailsCreateDto studentDetailsCreateDto,
		@RequestPart("files") List<MultipartFile> files,
		@RequestHeader("Authorization") String jwtToken
) {


	System.out.println("Received DTO: " + studentDetailsCreateDto + " filename " + files.get(0).getOriginalFilename());
	return bffService.test(studentDetailsCreateDto, jwtToken);
}


}
