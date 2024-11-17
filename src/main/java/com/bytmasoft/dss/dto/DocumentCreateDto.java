package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.enums.DocumentType;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class DocumentCreateDto {

private String fileName;
private String originalFileName;
private String filePath;
private Integer version;
MultipartFile file;
DocumentType documentType;

}
