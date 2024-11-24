package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.GuardianResponseDto;
import com.bytmasoft.dss.dto.StudentCreateDto;
import com.bytmasoft.dss.dto.StudentResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {

private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
private final WebClientUtil webClientUtil;
private final ServicesProperties servicesProperties;


/**
 * Add student details to dss
 *
 * @param studentCreateDto
 * @param token
 * @return
 */
public Mono<StudentResponseDto> saveStudent(StudentCreateDto studentCreateDto, String token) {
	System.out.println("Student url : " + servicesProperties.getStudentServiceStudent().getBaseUrl());
	logger.debug("Received studentCreateDto: {}", studentCreateDto.toString());
	return webClientUtil.saveEntity(servicesProperties.getStudentServiceStudent().getBaseUrl(), studentCreateDto, StudentResponseDto.class, token);
}


public Mono<StudentResponseDto> getStudentDetails(Long studentId, String jwtToken) {
	String uri = String.format("%s/%d", servicesProperties.getStudentServiceStudent().getBaseUrl(), studentId);
	return webClientUtil.fetchOne(uri, StudentResponseDto.class, jwtToken)
			       .doOnSuccess(student -> logger.info("Successfully fetched student data: {}", student))
			       .doOnError(throwable -> logger.error("Failed to fetch student for ID: {}", studentId, throwable));

}

public Mono<List<GuardianResponseDto>> getGuardianResponseDto(Long studentId, String jwtToken) {
	String uri = String.format("%s/%d/guardians/", servicesProperties.getStudentServiceStudent().getBaseUrl(), studentId);

	return webClientUtil.fetchList(uri, GuardianResponseDto.class, jwtToken)
			       .doOnSuccess(guardianResponseDto -> logger.info("Successfully fetched guardianResponseDtos data: {}", guardianResponseDto))
			       .doOnError(throwable -> logger.error("Failed to fetch guardianResponseDtos for ID: {}", studentId, throwable));
}


//    
//    public StudentDetailDTO getStudentDetails(Long studentId, String authorizationHeader) {
//        Mono<Student> student = webClient
//
//                .get()
//                .uri(servicesProperties.getStudentService().getBaseUrl()+"/{studentId}", studentId)
//                .header(HttpHeaders.AUTHORIZATION, authorizationHeader )
//                .header("Content-Type", "application/json")
//                .header("Accept", "application/json")
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> {
//                    logger.error("Client error occurred while fetching student with ID: {}", studentId);
//                    return WebClientErrorHandler.handleError(response);
//                })
//                .onStatus(HttpStatus::is5xxServerError, response -> {
//                    logger.error("Client error occurred while fetching student with ID: {}", studentId);
//                    return WebClientErrorHandler.handleError(response);
//                })
//                .bodyToMono(Student.class)
//                .doOnError(throwable -> {
//                    logger.error("Error fetching student with Id : {}", studentId, throwable.getMessage());
//                });
//
//            StudentDetailDTO studentDetailDTO = studentDetailMapper.studentToStudentDetailDTO(student.block());
//        studentDetailDTO.setTeachers(Collections.emptyList());
//        studentDetailDTO.setCourses(Collections.emptyList());
//        return studentDetailDTO;
//    }
}
