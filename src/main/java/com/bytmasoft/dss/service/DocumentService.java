package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.DocumentDto;
import com.bytmasoft.dss.dto.DocumentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final WebClientUtil webClientUtil;

    public Mono<DocumentDto> getDocumentById(Long studentId, String jwtToken){
    return null;
    }

}
