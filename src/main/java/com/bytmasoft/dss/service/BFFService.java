package com.bytmasoft.dss.service;

import com.bytmasoft.dss.dto.BffResponseDTO;
import com.bytmasoft.dss.dto.StudentDetailDTO;
import reactor.core.publisher.Mono;

public interface BFFService {

    public Mono<BffResponseDTO> getBffData(Long studentId, Long teacherId, Long documentId) ;

    StudentDetailDTO getStudentDetail(Long studentId, String authorizationHeader);
}
