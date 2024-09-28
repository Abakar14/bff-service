package com.bytmasoft.dss.service;

import com.bytmasoft.dss.dto.BffResponseDTO;
import com.bytmasoft.dss.dto.StudentDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class BFFServiceImpl implements BFFService {

    private final StudentServiceImpl studentService;
    private final TeacherServiceImpl teacherService;
   private final DocumentServiceImpl documentService;

    @Override
    public Mono<BffResponseDTO> getBffData(Long studentId, Long teacherId, Long documentId) {

        return Mono.zip(
                studentService.getStudentById(studentId),
                teacherService.getTeacherById(teacherId),
                documentService.getDocumentById(documentId)
        ).map(tuple -> {
            BffResponseDTO response = new BffResponseDTO();
            response.setStudent(tuple.getT1());
            response.setTeacher(tuple.getT2());
            response.setDocument(tuple.getT3());
            return response;
        });
    }

    @Override
    public StudentDetailDTO getStudentDetail(Long studentId, String authorizationHeader) {
        return null;
    }
}
