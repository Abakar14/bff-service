package com.bytmasoft.dss.service;

import com.bytmasoft.dss.dto.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class BFFService {

    private final WebClient webClient;
    private final StudentService studentService;
    private final TeacherService teacherService;
    DocumentService documentService;


    //return a compination of address, class, course, teacher, document
    public Mono<StudentResponse> getStudentData(Long studentId, String jwtToken) {

        return Mono.zip(
                studentService.getStudentDetails(studentId, jwtToken),
                teacherService.getTeacherById(studentId, jwtToken)
//                documentService.getDocumentById(studentId, jwtToken)
        ).map(tuple -> {
            return StudentResponse.builder()
                    .student(tuple.getT1())
                    .teacher(tuple.getT2())
//                    .document(tuple.getT3())
                    .build();
        });
    }



   /* public Mono<BffResponseDTO> getBffData(Long studentId, Long teacherId, Long documentId) {

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
    }*/

}
