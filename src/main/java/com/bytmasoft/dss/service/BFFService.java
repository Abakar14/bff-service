package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.dto.*;
import com.bytmasoft.dss.enums.DocumentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


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


/*    public Mono<StudentDetailsDto> addStudentDetailsWithFiles(StudentDetailsDto studentDatailsDto, String jwtToken) {

        Mono<AddressDto> addressDtoMono = schoolService.saveAddress(studentDatailsDto.getAddressDto(), jwtToken);


        Mono<List<DocumentDto>> documentDtoList = addressDtoMono.flatMap(addressDto -> {
            studentDatailsDto.getStudent().setAddressId(addressDto.getId());
            studentDatailsDto.getGuardianDto().setAddressId(addressDto.getId());
            DocumentDto documentDto = DocumentDto.builder()
                    .fileName("Test.txt")
                    .originalFileName("Test.txt")
                    .build();
            studentDatailsDto.getDocumentDtos().addAll(documentDto);
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

       *//* return  Mono.zip(studentDtoMono, guardianDtoMono, addressDtoMono)
                .map(tuple -> {
                 StudentDetailsDto result = StudentDetailsDto.builder()
                         .student(tuple.getT1())
                         .guardianDto(tuple.getT2())
                         .addressDto(tuple.getT3())
                         .build();
                 return result;
                });*//*
    }*/


//return a compination of address, class, course, teacher, document
public Mono<StudentResponse> getStudentDetails(Long studentId, String jwtToken) {
	// Call Student Service (note: use the service name from Eureka, not a hardcoded URL)
	Mono<StudentDto> studentDtoMono = webClient
			                                  .get()
			                                  .uri(servicesProperties.getStudentServiceStudent().getBaseUrl() + "/" + studentId)
			                                  .header("Authorization", jwtToken)
			                                  .retrieve()
			                                  .bodyToMono(StudentDto.class);


	// Call Teacher Service based on student response
	Mono<AddressDto> addressDtoMono = studentDtoMono.flatMap(studentDto ->
			                                                         webClient.get()
					                                                         .uri(servicesProperties.getSchoolServiceAddress().getBaseUrl() + "/{id}", studentDto.getAddressId())
					                                                         .header("Authorization", jwtToken)
					                                                         .retrieve()
					                                                         .bodyToMono(AddressDto.class));


	return Mono.zip(studentDtoMono, addressDtoMono)
			       .map(tuple -> new StudentResponse(tuple.getT1(), tuple.getT2()));
}

public Mono<StudentDto> getStudent(Long studentId, String jwtToken) {
	return webClient
			       .get()
			       .uri(servicesProperties.getStudentServiceStudent().getBaseUrl() + "/" + studentId)
			       .header("Authorization", jwtToken)
			       .retrieve()
			       .bodyToMono(StudentDto.class);
}

public Mono<StudentDetailsDto> addStudentDetailsWithFiles(StudentDetailsCreateDto studentDetailsCreateDto, List<MultipartFile> files, List<DocumentType> documentTypes, String jwtToken) {

	return schoolService.saveAddress(studentDetailsCreateDto.getAddressCreateDto(), jwtToken)
			       .doOnNext(addressDto -> {
				       System.out.println("Address saved with ID: " + addressDto.getId());

				       studentDetailsCreateDto.getStudentCreateDto().setAddressId(addressDto.getId());
				       studentDetailsCreateDto.getGuardianCreateDto().setAddressId(addressDto.getId());
			       })
			       .flatMap(addressDto -> {
				       System.out.println("Saving student with address ID: " + addressDto.getId());
				       List<GuardianCreateDto> guardianCreateDtoList = new ArrayList<>();
				       guardianCreateDtoList.add(studentDetailsCreateDto.getGuardianCreateDto());

				       if (studentDetailsCreateDto.getStudentCreateDto().getGuardianCreateDtos() == null) {
					       studentDetailsCreateDto.getStudentCreateDto().setGuardianCreateDtos(new ArrayList<>());
					       studentDetailsCreateDto.getStudentCreateDto().getGuardianCreateDtos().addAll(guardianCreateDtoList);

				       }
				       return studentService.saveStudent(studentDetailsCreateDto.getStudentCreateDto(), jwtToken);
			       })
			       .doOnNext(studentResponseDto -> {
				       System.out.println("Student saved with ID: " + studentResponseDto.getId());
			       })
			       .flatMap(studentDto -> {
				       System.out.println("Uploading documents for student ID: " + studentDto.getId());
				       return documentService.uploadDocuments(files, documentTypes, studentDto.getId(), jwtToken)
						              .map(documents -> {

							              StudentDetailsDto studentDetailsDto =
									              com.bytmasoft.dss.dto.StudentDetailsDto.builder()
											              .documentDtos(documents)
											              //.studentDto(studentResponseDto)
											              .build();

							              return studentDetailsDto;
						              });
			       })
			       .doOnError(e -> System.err.println("Error in chain: " + e.getMessage()))
			       .onErrorResume(e -> {
				       // Ensure errors are caught and logged
				       return Mono.error(new RuntimeException("Failed to complete process", e));
			       });
}


}
