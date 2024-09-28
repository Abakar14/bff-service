package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.StudentDetailDTO;
import com.bytmasoft.dss.entity.Student;
import com.bytmasoft.dss.exception.WebClientErrorHandler;
import com.bytmasoft.dss.mapper.StudentDetailMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final WebClientUtil webClientUtil;
    private final StudentDetailMapper studentDetailMapper;
    private final ServicesProperties servicesProperties;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final GenericResponseService responseBuilder;

    @Override
    public Mono<StudentDetailDTO> getStudentDetails(Long studentId, String authorizationHeader) {
        logger.info("Starting request to fetch student with ID: {}", studentId);

        return webClientUtil.get(servicesProperties.getStudentService().getBaseUrl()+studentId, StudentDetailDTO.class)
                .doOnSuccess(student -> logger.info("Successfully fetched teacher data: {}", student))
                .doOnError(throwable -> logger.error("Failed to fetch teacher for ID: {}", studentId, throwable));
    }

    @Override
    public Mono<StudentDetailDTO> getStudentById(Long studentId) {
        return null;
    }


//    @Override
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
