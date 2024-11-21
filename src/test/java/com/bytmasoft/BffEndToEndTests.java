package com.bytmasoft;

import com.bytmasoft.dss.dto.*;
import com.bytmasoft.dss.enums.DocumentType;
import com.bytmasoft.dss.enums.Gender;
import com.bytmasoft.dss.service.BFFService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class BffEndToEndTests {

@LocalServerPort
private int port;
private String baseUrl;

@Autowired
private WebTestClient webTestClient;

private String jwtToken;
@MockBean
private BFFService bffService;

@BeforeEach
void setUp() {
	baseUrl = "http://localhost:" + port + "/dss/api/v1";
	//jwtToken = JwtTokenUtil.generateToken();
	jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6ImFiYWthciIsImlhdCI6MTczMTY3Mzk2NSwiZXhwIjoxNzMxNjc1NzY1fQ.oe4f93BxBaf23zh5G0UvEjRNNt4sGO1qP6VDTAjLm2E";
}


@Test
void testAddStudentDetails() throws Exception {
	// Mock input data
	StudentDetailsCreateDto studentDetailsCreateDto = StudentDetailsCreateDto.builder()
			                                                  .studentCreateDto(StudentCreateDto.builder()
					                                                                    .firstName("John")
					                                                                    .lastName("Doe")
					                                                                    .birthPlace("Berlin")
					                                                                    .gender(Gender.MALE)
					                                                                    .birthDate("2000-01-01")
					                                                                    .country("Germany")
					                                                                    .email("john.doe@example.com")
					                                                                    .mobile("1234567890")
					                                                                    .phone("0987654321")
					                                                                    .build())
			                                                  .addressCreateDto(AddressCreateDto.builder()
					                                                                    .city("Berlin")
					                                                                    .country("Germany")
					                                                                    .street("Main Street")
					                                                                    .streetNumber("123")
					                                                                    .zipCode("10115")
					                                                                    .build())
			                                                  .guardianCreateDto(GuardianCreateDto.builder()
					                                                                     .firstName("Jane")
					                                                                     .lastName("Doe")
					                                                                     .country("Germany")
					                                                                     .email("jane.doe@example.com")
					                                                                     .mobile("0987654321")
					                                                                     .phone("1234567890")
					                                                                     .gender(Gender.FEMALE)
					                                                                     .build())
			                                                  .build();

	byte[] file1Content = "Sample content for file 1".getBytes(StandardCharsets.UTF_8);
	byte[] file2Content = "Sample content for file 2".getBytes(StandardCharsets.UTF_8);

	MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
	bodyBuilder.part("studentDetailsCreateDto", new ObjectMapper().writeValueAsString(studentDetailsCreateDto))
			.header("Content-Type", MediaType.APPLICATION_JSON_VALUE);

	bodyBuilder.part("files", new ByteArrayResource(file1Content) {
		@Override
		public String getFilename() {
			return "file1.txt";
		}
	}).header("Content-Disposition", "form-data; name=files; filename=file1.txt");

	bodyBuilder.part("files", new ByteArrayResource(file2Content) {
		@Override
		public String getFilename() {
			return "file2.txt";
		}
	}).header("Content-Disposition", "form-data; name=files; filename=file2.txt");

	bodyBuilder.part("documentTypes", DocumentType.PROFILE_PICTURE.name());
	bodyBuilder.part("documentTypes", DocumentType.BIRTH_CERTIFICATE.name());
	bodyBuilder.part("ownerId", "123");

	// Mock service response
	StudentDetailsResponseDto mockResponse = StudentDetailsResponseDto.builder()
			                                         .studentDto(StudentDto.builder().firstName("John").build())
			                                         .build();

	Mockito.when(bffService.addStudentDetailsWithFiles(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
			.thenReturn(Mono.just(mockResponse));

	// Make the request
	webTestClient.post()
			.uri(baseUrl + "/bff/student-details")
			.header("Authorization", jwtToken)
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.body(BodyInserters.fromMultipartData(bodyBuilder.build()))
			.exchange()
			.expectStatus().isOk()
			.expectBody(StudentDetailsResponseDto.class)
			.value(response -> {
				assertNotNull(response);
				assertEquals("John", response.getStudentDto().getFirstName());
			});

	// Verify interaction
	Mockito.verify(bffService, Mockito.times(1))
			.addStudentDetailsWithFiles(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

}

}
