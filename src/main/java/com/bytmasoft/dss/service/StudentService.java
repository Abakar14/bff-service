package com.bytmasoft.dss.service;

import com.bytmasoft.dss.dto.StudentDetailDTO;
import reactor.core.publisher.Mono;

public interface StudentService {

    Mono<StudentDetailDTO> getStudentDetails(Long studentId, String authorizationHeader);

    Mono<StudentDetailDTO> getStudentById(Long studentId);
}
