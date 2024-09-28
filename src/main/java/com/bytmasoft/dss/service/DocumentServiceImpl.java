package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.DocumentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.DocumentStartToken;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final WebClientUtil webClientUtil;

    public Mono<DocumentDTO> getDocumentById(Long id){
        return webClientUtil.get("url" +id, DocumentDTO.class);
    }

}
