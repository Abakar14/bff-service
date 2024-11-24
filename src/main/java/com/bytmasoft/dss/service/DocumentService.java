package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.DocumentDto;
import com.bytmasoft.dss.dto.DocumentResponseDto;
import com.bytmasoft.dss.enums.DocumentType;
import com.bytmasoft.dss.enums.OwnerType;
import com.bytmasoft.dss.exception.FileProcessingException;
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
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class DocumentService {

private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

private final WebClientUtil webClientUtil;
private final ServicesProperties servicesProperties;
private final WebClient webClient;


public Mono<List<DocumentResponseDto>> uploadDocuments(List<MultipartFile> files, List<DocumentType> documentTypes, OwnerType ownerType, Long ownerId, Long schoolId, String jwtToken) {

	MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();

	IntStream.range(0, files.size()).forEach(i -> {
		try {
			MultipartFile file = files.get(i);
			String filename = file.getOriginalFilename();
			byte[] fileBytes = file.getBytes();

			ByteArrayResource fileResource = new ByteArrayResource(fileBytes) {
				@Override
				public String getFilename() {
					return filename;
				}
			};

			bodyBuilder.part("files", fileResource)
					.header("Content-Disposition", "form-data; name=files; filename=" + filename)
					.header("Content-Type", file.getContentType());
			bodyBuilder.part("documentTypes", documentTypes.get(i).name());
			bodyBuilder.part("ownerType", ownerType.name());
			bodyBuilder.part("schoolId", schoolId);
			logger.debug("Added file part: {}, type: {}", filename, documentTypes.get(i));

		} catch (IOException e) {
			throw new FileProcessingException("Error processing file: " + files.get(i).getOriginalFilename(), e);
		}
	});

	bodyBuilder.part("ownerId", ownerId.toString());

	return webClient.post()
			       .uri(servicesProperties.getStorageService().getBaseUrl() + "/uploads/files")
			       .header("Authorization", jwtToken)
			       .contentType(MediaType.MULTIPART_FORM_DATA)
			       .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
			       .retrieve()
			       .bodyToMono(new ParameterizedTypeReference<List<DocumentResponseDto>>() {
			       })
			       .doOnSuccess(documents -> logger.info("Uploaded {} documents", documents.size()))
			       .doOnError(e -> logger.error("Document upload failed: {}", e.getMessage(), e));
}

public Mono<List<DocumentDto>> saveDocument(DocumentDto documentDto, String jwtToken) {
	return webClientUtil.saveEntities(servicesProperties.getStorageService().getBaseUrl(), documentDto, DocumentDto.class, jwtToken);
}

}