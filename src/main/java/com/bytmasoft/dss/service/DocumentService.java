package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.DocumentDto;
import com.bytmasoft.dss.dto.DocumentResponseDto;
import com.bytmasoft.dss.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final WebClientUtil webClientUtil;
    private final ServicesProperties servicesProperties;




    public Mono<DocumentDto> getDocumentById(Long studentId, String jwtToken){
    return null;
    }

    public Mono<List<DocumentDto>> saveDocument(DocumentDto documentDto, String jwtToken) {
        return webClientUtil.saveEntities(servicesProperties.getStorageService().getBaseUrl(), documentDto, DocumentDto.class, jwtToken );

    }
}
