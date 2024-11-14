package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.DocumentDto;
import com.bytmasoft.dss.enums.DocumentType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentService {

private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
private final WebClientUtil webClientUtil;
private final ServicesProperties servicesProperties;
private final WebClient webClient;


public Mono<List<DocumentDto>> uploadDocuments(
		List<MultipartFile> files, List<DocumentType> documentTypes, Long ownerId, String jwtToken) {
	System.out.println("doc url : " + servicesProperties.getStorageService().getBaseUrl() + "/uploads/files");
	System.out.printf("documentTypes : " + documentTypes.toString());


	MultipartBodyBuilder builder = new MultipartBodyBuilder();

	for (int i = 0; i < files.size(); i++) {

		try {
			// Store filename in a final variable to access it within the anonymous inner class
			final String originalFilename = files.get(i).getOriginalFilename();
			System.out.println("File Type : " + originalFilename);
			System.out.println(" documentType" + documentTypes.get(i));
			System.out.println(" ContentType " + files.get(i).getContentType());
			byte[] fileBytes = files.get(i).getBytes();

			ByteArrayResource byteArrayResource = new ByteArrayResource(fileBytes) {
				@Override
				public String getFilename() {
					return originalFilename; // Use the final variable here
				}
			};

			// Add file and document type to the multipart form data
			builder.part("files", byteArrayResource)
					.header("Content-Disposition", "form-data; name=files; filename=" + originalFilename)
					.header("Content-Type", files.get(i).getContentType());
			builder.part("documentTypes", documentTypes.get(i).name());
		} catch (IOException e) {
			throw new RuntimeException("Error reading file input stream", e);
		}

	}
	builder.part("ownerId", ownerId.toString());

	return webClient.post()
			       .uri(servicesProperties.getStorageService().getBaseUrl() + "/uploads/files")
			       .header("Authorization", jwtToken)
			       .contentType(MediaType.MULTIPART_FORM_DATA)
			       .body(BodyInserters.fromMultipartData(builder.build()))
			       .retrieve()
			       .bodyToMono(new ParameterizedTypeReference<List<DocumentDto>>() {
			       });
}

public Mono<List<DocumentDto>> saveDocument(DocumentDto documentDto, String jwtToken) {
	return webClientUtil.saveEntities(servicesProperties.getStorageService().getBaseUrl(), documentDto, DocumentDto.class, jwtToken);

}
}
