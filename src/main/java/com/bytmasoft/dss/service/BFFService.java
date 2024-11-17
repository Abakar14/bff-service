package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.dto.*;
import com.bytmasoft.dss.enums.DocumentType;
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


public Mono<StudentDto> getStudent(Long studentId, String jwtToken) {
	return webClient
			       .get()
			       .uri(servicesProperties.getStudentServiceStudent().getBaseUrl() + "/" + studentId)
			       .header("Authorization", jwtToken)
			       .retrieve()
			       .bodyToMono(StudentDto.class);
}

public Mono<StudentDetailsDto> addStudentDetailsWithFiles(StudentDetailsCreateDto studentDetailsCreateDto,
                                                          List<MultipartFile> files, List<DocumentType> documentTypes,
                                                          String jwtToken) {
	// Log request details
	logger.info("Adding student details with files for user: {}", bffUtils.getUsername());

	return saveAddress(studentDetailsCreateDto.getAddressCreateDto(), jwtToken)
			       .flatMap(addressDto -> processStudent(studentDetailsCreateDto, addressDto, jwtToken))
			       .flatMap(studentDetailsDto -> uploadDocumentsForStudent(studentDetailsDto, files, documentTypes, jwtToken))
			       .doOnSuccess(result -> logger.info("Successfully processed student details: {}", result))
			       .doOnError(e -> logger.error("Failed to process student details: {}", e.getMessage(), e))
			       .onErrorResume(e -> Mono.error(new CustomProcessingException("Failed to process student details", e)));
}

private Mono<AddressDto> saveAddress(AddressCreateDto addressDto, String jwtToken) {
	logger.debug("Saving address: {}", addressDto);
	return schoolService.saveAddress(addressDto, jwtToken)
			       .doOnSuccess(savedAddress -> logger.info("Address saved with ID: {}", savedAddress.getId()));
}


private Mono<StudentDetailsDto> processStudent(StudentDetailsCreateDto studentDetailsCreateDto,
                                               AddressDto addressDto, String jwtToken) {

	// Update student and guardian with address ID
	studentDetailsCreateDto.getStudentCreateDto().setAddressId(addressDto.getId());
	studentDetailsCreateDto.getStudentCreateDto().getGuardianCreateDtos().forEach(guardian -> {
		guardian.setAddressId(addressDto.getId());
	});

	return studentService.saveStudent(studentDetailsCreateDto.getStudentCreateDto(), jwtToken)
			       .map(studentDto -> {
				       logger.info("Student saved with ID: {}", studentDto.getId());
				       return StudentDetailsDto.builder()
						              .studentDto(studentDto)
						              .addressDto(addressDto)
						              .build();
			       });
}

private Mono<StudentDetailsDto> uploadDocumentsForStudent(StudentDetailsDto studentDetailsDto,
                                                          List<MultipartFile> files, List<DocumentType> documentTypes, String jwtToken) {

	Long studentId = studentDetailsDto.getStudentDto().getId();
	logger.debug("Uploading documents for student ID: {}", studentId);

	return documentService.uploadDocuments(files, documentTypes, studentId, jwtToken)
			       .map(documents -> {
				       logger.info("Uploaded {} documents for student ID: {}", documents.size(), studentId);
				       studentDetailsDto.setDocumentDtos(documents);
				       return studentDetailsDto;
			       });
}


/*public Mono<StudentDetailsDto> addStudentDetailsWithFiles(StudentDetailsCreateDto studentDetailsCreateDto, List<MultipartFile> files, List<DocumentType> documentTypes, String jwtToken) {

	StudentDetailsDto studentDetailsDto = StudentDetailsDto.builder().build();
	System.out.println("Username from context : " + bffUtils.getUsername());

	return schoolService.saveAddress(studentDetailsCreateDto.getAddressCreateDto(), jwtToken)
			       .doOnNext(addressDto -> {
				       System.out.println("Address saved with ID: " + addressDto.getId());

				       studentDetailsCreateDto.getStudentCreateDto().setAddressId(addressDto.getId());
				       studentDetailsCreateDto.getStudentCreateDto().getGuardianCreateDtos().forEach(guardianCreateDto -> {
					       guardianCreateDto.setAddressId(addressDto.getId());
				       });

				       studentDetailsDto.setAddressDto(addressDto);
			       })
			       .flatMap(addressDto -> {
				       return studentService.saveStudent(studentDetailsCreateDto.getStudentCreateDto(), jwtToken);
			       })
			       .doOnNext(studentDto -> {
				       System.out.println("Student saved with ID: " + studentDto.getId());
				       studentDetailsDto.setStudentDto(studentDto);
			       })
			       .flatMap(studentDto -> {
				       System.out.println("Uploading documents for student ID: " + studentDto.getId());
				       return documentService.uploadDocuments(files, documentTypes, studentDto.getId(), jwtToken)
						              .map(documents -> {

							              studentDetailsDto.setDocumentDtos(documents);

							              return studentDetailsDto;
						              });
			       })
			       .doOnError(e -> System.err.println("Error in chain: " + e.getMessage()))
			       .onErrorResume(e -> {
				       // Ensure errors are caught and logged
				       return Mono.error(new RuntimeException("Failed to complete process", e));
			       });
}*/


public StudentDetailsDto test(StudentDetailsCreateDto studentDetailsCreateDto, String jwtToken) {
	return StudentDetailsDto.builder()
			       .studentDto(StudentDto.builder()
					                   .firstName("John")
					                   .build())
			       .build();
}
}
