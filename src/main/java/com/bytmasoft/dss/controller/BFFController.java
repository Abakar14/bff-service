package com.bytmasoft.dss.controller;

import com.bytmasoft.dss.dto.StudentDetailsCreateDto;
import com.bytmasoft.dss.dto.StudentResponseDto;
import com.bytmasoft.dss.enums.DocumentType;
import com.bytmasoft.dss.enums.OwnerType;
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

@PreAuthorize("hasAnyAuthority('MANAGE_USERS')")
@PostMapping(value = "/student-details", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
public StudentResponseDto addStudentDetails(@RequestPart("studentDetailsCreateDto") StudentDetailsCreateDto studentDetailsCreateDto,
                                            @RequestPart("files") List<MultipartFile> files,
                                            @RequestParam("documentTypes") List<DocumentType> documentTypes,
                                            @RequestParam("ownerType") OwnerType ownerType,
                                            @RequestParam("schoolId") Long schoolId,
                                            @RequestHeader("Authorization") String jwtToken) {

	logger.debug("Received StudentDetailsCreateDto: {}", studentDetailsCreateDto);
	logger.debug("Received Files: {}", files.size());
	logger.debug("Received Document Types: {}", documentTypes);
	logger.debug("Received Owner Type: {}", ownerType);

	System.out.println("Received StudentDetailsCreateDto: {}" + studentDetailsCreateDto.toString());
	System.out.println("Received Files: {}" + files.size());
	System.out.println("Received Document Types: {}" + documentTypes);
	System.out.println("Received Owner Type: {}" + ownerType);

	return bffService.addStudentDetailsWithFiles(studentDetailsCreateDto, files, documentTypes, ownerType, schoolId, jwtToken)
			       .switchIfEmpty(Mono.error(new IllegalStateException("No response generated"))).block();
}

@PreAuthorize("hasAnyAuthority('MANAGE_USERS')")
@GetMapping("/student-details/{studentId}")
public StudentResponseDto getStudentDetails(@PathVariable Long studentId, @RequestHeader("Authorization") String jwtToken) {
	return bffService.getStudentResponseDto(studentId, jwtToken)
			       .switchIfEmpty(Mono.error(new IllegalStateException("No response founded"))).block();

}


}
