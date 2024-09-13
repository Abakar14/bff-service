package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.entity.Student;
import com.bytmasoft.dss.entity.StudentDetails;
import com.bytmasoft.dss.entity.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class BFFServiceImpl implements BFFService {

    private final WebClient webClient;
    private final ServicesProperties servicesProperties;


    @Override
    public StudentDetails getStudentDetails(Long studentId, String authorizationHeader) {
        Mono<Student> student = webClient

                .get()
                .uri(servicesProperties.getStudentService().getBaseUrl()+"/{studentId}", studentId)
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader )
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(Student.class)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to fetch student details")));

        Mono<Teacher> teacher = webClient
                .get()
                .uri(servicesProperties.getStudentService().getBaseUrl()+"/{studentId}", studentId)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(Teacher.class);

        StudentDetails studentDetails = new StudentDetails(student.block(), null);
        return studentDetails;
    }

}
