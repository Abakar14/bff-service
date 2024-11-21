package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.dto.*;
import com.bytmasoft.dss.enums.DocumentType;
import com.bytmasoft.dss.enums.OwnerType;
import com.bytmasoft.dss.exception.CustomProcessingException;
import com.bytmasoft.dss.utils.BFFUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BFFService {

private static final Logger logger = LoggerFactory.getLogger(BFFService.class);

private final WebClient webClient;
private final StudentService studentService;
private final TeacherService teacherService;
private final SchoolService schoolService;
private final DocumentService docService;
private final ServicesProperties servicesProperties;
private final DocumentService documentService;
private final BFFUtils bffUtils;


public StudentDetailsResponseDto getStudentDetailsResponseDto(Long id, String jwtToken) {

	getStudentDetails(id, jwtToken)
			.flatMap(studentResponseDto -> getTeachers(studentResponseDto, jwtToken))
			.flatMap()
	// 1- get student 2- get profile picture
	// 3- get teachers 4- get class info
	// 5- get guardian
	// 6- get Address ==> ok
	// 7- get School name ==> ok

	return null;
}

private Mono<SchoolResponseDto> getSchoolResponseDto(StudentResponseDto studentResponseDto, String jwtToken) {
	return nuul;
}
private Mono<List<AddressResponseDto>> getAddress(StudentResponseDto studentResponseDto, String jwtToken) {
	return nuul;
}
private Mono<List<GuardianResponseDto>> getGuardian(StudentResponseDto studentResponseDto, String jwtToken) {
	return nuul;
}
private Mono<List<TeacherResponseDto>> getTeachers(StudentResponseDto studentResponseDto, String jwtToken) {
	return nuul;
}
private Mono<StudentResponseDto> getStudentDetails(Long id, String jwtToken) {
	return nuul;
}


public Mono<StudentDetailsResponseDto> addStudentDetailsWithFiles(StudentDetailsCreateDto studentDetailsCreateDto,
                                                                  List<MultipartFile> files, List<DocumentType> documentTypes,
                                                                  OwnerType ownerType,
                                                                  String jwtToken) {
	// Log request details
	logger.info("Adding student details with files for user: {}", bffUtils.getUsername());

	return saveAddress(studentDetailsCreateDto.getAddressCreateDto(), jwtToken)
			       .flatMap(addressDto -> processStudent(studentDetailsCreateDto, addressDto, jwtToken))
			       .flatMap(studentDetailsDto -> uploadDocumentsForStudent(studentDetailsDto, files, documentTypes, ownerType, jwtToken))
			       .doOnSuccess(result -> logger.info("Successfully processed student details: {}", result))
			       .doOnError(e -> logger.error("Failed to process student details: {}", e.getMessage(), e))
			       .onErrorResume(e -> Mono.error(new CustomProcessingException("Failed to process student details", e)));
}

private Mono<AddressResponseDto> saveAddress(AddressCreateDto addressDto, String jwtToken) {
	logger.debug("Saving address: {}", addressDto);
	return schoolService.saveAddress(addressDto, jwtToken)
			       .doOnSuccess(savedAddress -> logger.info("Address saved with ID: {}", savedAddress.getId()));
}


private Mono<StudentDetailsResponseDto> processStudent(StudentDetailsCreateDto studentDetailsCreateDto,
                                                       AddressResponseDto addressDto, String jwtToken) {
	// Update student and guardian with address ID
	studentDetailsCreateDto.getStudentCreateDto().setAddressId(addressDto.getId());
	studentDetailsCreateDto.getStudentCreateDto().getGuardianCreateDtos().forEach(guardian -> {
		guardian.setAddressId(addressDto.getId());
	});

	return studentService.saveStudent(studentDetailsCreateDto.getStudentCreateDto(), jwtToken)
			       .map(studentDto -> {
				       logger.info("Student saved with ID: {}", studentDto.getId());
				       return StudentDetailsResponseDto.builder()
						              .studentDto(studentDto)
						              .addressDto(addressDto)
						              .build();
			       });
}

private Mono<StudentDetailsResponseDto> uploadDocumentsForStudent(StudentDetailsResponseDto studentDetailsDto,
                                                                  List<MultipartFile> files, List<DocumentType> documentTypes, OwnerType ownerType, String jwtToken) {

	Long studentId = studentDetailsDto.getStudentDto().getId();
	logger.debug("Uploading documents for student ID: {}", studentId);

	return documentService.uploadDocuments(files, documentTypes, ownerType, studentId, jwtToken)
			       .map(documents -> {
				       logger.info("Uploaded {} documents for student ID: {}", documents.size(), studentId);
				       studentDetailsDto.setDocumentDtos(documents);
				       return studentDetailsDto;
			       });
}



}
