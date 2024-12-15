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


public Mono<StudentResponseDto> getStudentResponseDto(Long studentId, String jwtToken) {

	Mono<StudentResponseDto> studentResponseDtoMono = getStudentDetails(studentId, jwtToken);

	Mono<List<GuardianResponseDto>> guardianResponseDtoMono = getGuardianResponse(studentId, jwtToken);

	Mono<AddressResponseDto> addressResponseDtoMono = getAddressResponseDto(studentResponseDtoMono.block().getAddressId(), jwtToken);

	//Combine all results into a single response
	return Mono.zip(studentResponseDtoMono, guardianResponseDtoMono, addressResponseDtoMono)
			       .map(tuple -> {
				       StudentResponseDto studentResponse = tuple.getT1();
				       List<GuardianResponseDto> guardians = tuple.getT2();
				       AddressResponseDto addressResponse = tuple.getT3();

				       studentResponse.setGuardianResponseDtos(guardians);
				       studentResponse.setAddressResponseDto(addressResponse);
				       return studentResponse;
			       }).doOnError(e -> logger.error("Error fetching student details: {}", e.getMessage()))
			       .onErrorResume(e -> Mono.error(new RuntimeException("Failed to fetch student details", e)));


}

private Mono<StudentResponseDto> getStudentDetails(Long studentId, String jwtToken) {
	return studentService.getStudentDetails(studentId, jwtToken);
}

private Mono<List<GuardianResponseDto>> getGuardianResponse(Long id, String jwtToken) {
	return studentService.getGuardianResponseDto(id, jwtToken);
}

private Mono<AddressResponseDto> getAddressResponseDto(Long addressId, String jwtToken) {

	return schoolService.getAddress(addressId, jwtToken);
}


private Mono<SchoolResponseDto> getSchool(Long schoolId, String jwtToken) {

	return schoolService.getSchool(schoolId, jwtToken)
			       .doOnSuccess(schoolResponseDto -> logger.info("School Id {}", schoolResponseDto.getId()));
}


/**
 * @param studentDetailsCreateDto
 * @param files
 * @param documentTypes
 * @param ownerType
 * @param schoolId
 * @param jwtToken
 * @return studentResponseDto
 */
public Mono<StudentResponseDto> addStudentDetailsWithFiles(StudentDetailsCreateDto studentDetailsCreateDto,
                                                           List<MultipartFile> files, List<DocumentType> documentTypes,
                                                           OwnerType ownerType,
                                                           Long schoolId,
                                                           String jwtToken) {
	// Log request details
	logger.info("Adding student details with files for user: {}", bffUtils.getUsername());

	return saveAddress(studentDetailsCreateDto.getAddressCreateDto(), jwtToken)
			       .flatMap(addressDto -> processStudent(studentDetailsCreateDto, addressDto, jwtToken))
			       .flatMap(studentResponseDto -> uploadDocumentsForStudent(studentResponseDto, files, documentTypes, ownerType, schoolId, jwtToken))
			       .doOnSuccess(result -> logger.info("Successfully processed student details: {}", result))
			       .doOnError(e -> logger.error("Failed to process student details: {}", e.getMessage(), e))
			       .onErrorResume(e -> Mono.error(new CustomProcessingException("Failed to process student details", e)));
}

private Mono<AddressResponseDto> saveAddress(AddressCreateDto addressDto, String jwtToken) {
	logger.debug("Saving address: {}", addressDto);
	return schoolService.saveAddress(addressDto, jwtToken)
			       .doOnSuccess(savedAddress -> logger.info("Address saved with ID: {}", savedAddress.getId()));
}


private Mono<StudentResponseDto> processStudent(StudentDetailsCreateDto studentDetailsCreateDto,
                                                AddressResponseDto addressDto, String jwtToken) {
	// Update student and guardian with address ID
	studentDetailsCreateDto.getStudentCreateDto().setAddressId(addressDto.getId());
	studentDetailsCreateDto.getStudentCreateDto().getGuardianCreateDtos().forEach(guardian -> {
		guardian.setAddressId(addressDto.getId());
	});

	return studentService.saveStudent(studentDetailsCreateDto.getStudentCreateDto(), jwtToken)
			       .map(studentDto -> {
				       logger.info("Student saved with ID: {}", studentDto.getId());

				       studentDto.setAddressResponseDto(addressDto);

				       return studentDto;

			       });
}

private Mono<StudentResponseDto> uploadDocumentsForStudent(StudentResponseDto studentResponseDto,
                                                           List<MultipartFile> files, List<DocumentType> documentTypes, OwnerType ownerType, Long schoolId, String jwtToken) {

	Long studentId = studentResponseDto.getId();
	logger.debug("Uploading documents for student ID: {}", studentId);

	return documentService.uploadDocuments(files, documentTypes, ownerType, studentId, schoolId, jwtToken)
			       .map(documents -> {
				       logger.info("Uploaded {} documents for student ID: {}", documents.size(), studentId);
				       studentResponseDto.setDocumentResponseDtos(documents);
				       return studentResponseDto;
			       });
}


}
