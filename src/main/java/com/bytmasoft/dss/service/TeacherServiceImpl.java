package com.bytmasoft.dss.service;

import com.bytmasoft.dss.dto.TeacherDetailDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TeacherServiceImpl implements TeacherService {


    @Override
    public Mono<TeacherDetailDTO> getTeacherById(Long teacherId) {
        return null;
    }
}
