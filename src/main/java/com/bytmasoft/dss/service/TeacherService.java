package com.bytmasoft.dss.service;

import com.bytmasoft.dss.dto.StudentDetailDTO;
import com.bytmasoft.dss.dto.TeacherDetailDTO;
import reactor.core.publisher.Mono;

public interface TeacherService {
    Mono<TeacherDetailDTO> getTeacherById(Long teacherId);
}
