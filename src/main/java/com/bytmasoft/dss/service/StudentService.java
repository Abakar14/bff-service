package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.StudentDto;
import com.bytmasoft.dss.mapper.StudentDetailMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final WebClientUtil webClientUtil;
    private final ServicesProperties servicesProperties;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
  //  private final StudentDetailMapper studentDetailMapper;
   // private final GenericResponseService responseBuilder;

    
    public Mono<StudentDto> getStudentDetails(Long studentId, String jwtToken) {
        logger.info("Starting request to fetch student with ID: {}", studentId);

        return webClientUtil.get(servicesProperties.getStudentService().getBaseUrl()+studentId, StudentDto.class)
                .doOnSuccess(student -> logger.info("Successfully fetched student data: {}", student))
                .doOnError(throwable -> logger.error("Failed to fetch student for ID: {}", studentId, throwable));
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
