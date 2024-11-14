package com.bytmasoft.dss.dto;

@lombok.Data
@lombok.Builder
public class DocumentCreateDto {

private String fileName;
private String originalFileName;
private String filePath;
private Integer version;
org.springframework.web.multipart.MultipartFile file;
org.w3c.dom.DocumentType documentType;
}
