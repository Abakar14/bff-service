package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.dto.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.service.GenericResponseService;
import org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.cloud.client.loadbalancer.reactive.RetryableLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BFFService {

    private final WebClient webClient;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SchoolService schoolService;
    private final DocumentService docService;
    private final ServicesProperties servicesProperties;
   private final DocumentService documentService;


    public Mono<StudentDetailsDto> addStudentDetails(StudentDetailsDto studentDatailsDto, String jwtToken) {

        Mono<AddressDto> addressDtoMono = schoolService.saveAddress(studentDatailsDto.getAddressDto(), jwtToken);

        Mono<List<DocumentDto>> documentDtoList = addressDtoMono.flatMap(addressDto -> {
            studentDatailsDto.getStudent().setAddressId(addressDto.getId());
            studentDatailsDto.getGuardianDto().setAddressId(addressDto.getId());
            DocumentDto documentDto = DocumentDto.builder()
                    .fileName("Test.txt")
                    .originalFileName("Test.txt")
                    .build();
            studentDatailsDto.setDocumentDto(documentDto);
            return docService.saveDocument(studentDatailsDto.getDocumentDto(), jwtToken);
        });

        Mono<GuardianDto> guardianDtoMono = documentDtoList.flatMap(documentDtos -> {
            List<Long> documentIds = documentDtos.stream().map(DocumentDto::getId).collect(Collectors.toList());
           studentDatailsDto.getStudent().setDocumentsIds(documentIds);

            return  studentService.saveGuardian(studentDatailsDto.getGuardianDto(), jwtToken);
        });

      return guardianDtoMono.flatMap(guardianDto -> {
          studentDatailsDto.getStudent().getGuardianIds().add(guardianDto.getId());
          return studentService.saveStudent( studentDatailsDto.getStudent(),jwtToken);
      }).map(savedStudent -> {
          studentDatailsDto.setStudent(savedStudent);
          return studentDatailsDto;
      });

       /* return  Mono.zip(studentDtoMono, guardianDtoMono, addressDtoMono)
                .map(tuple -> {
                 StudentDetailsDto result = StudentDetailsDto.builder()
                         .student(tuple.getT1())
                         .guardianDto(tuple.getT2())
                         .addressDto(tuple.getT3())
                         .build();
                 return result;
                });*/
    }


    //return a compination of address, class, course, teacher, document
    public Mono<StudentResponse> getStudentDetails(Long studentId, String jwtToken) {
        // Call Student Service (note: use the service name from Eureka, not a hardcoded URL)
        Mono<StudentDto> studentDtoMono = webClient
                .get()
                .uri(servicesProperties.getStudentServiceStudent().getBaseUrl()+"/"+studentId)
                .header("Authorization", jwtToken)
                .retrieve()
                .bodyToMono(StudentDto.class);




        // Call Teacher Service based on student response
        Mono<AddressDto> addressDtoMono = studentDtoMono.flatMap( studentDto ->
                webClient.get()
                    .uri(servicesProperties.getSchoolServiceAddress().getBaseUrl()+"/{id}",studentDto.getAddressId())
                        .header("Authorization", jwtToken)
                        .retrieve()
                        .bodyToMono(AddressDto.class));



        return Mono.zip(studentDtoMono, addressDtoMono)
                .map(tuple -> new StudentResponse(tuple.getT1(), tuple.getT2()));
    }

    public Mono<StudentDto> getStudent(Long studentId, String jwtToken) {
      return  webClient
                .get()
                .uri(servicesProperties.getStudentServiceStudent().getBaseUrl()+"/"+studentId)
                .header("Authorization", jwtToken)
                .retrieve()
                .bodyToMono(StudentDto.class);
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
