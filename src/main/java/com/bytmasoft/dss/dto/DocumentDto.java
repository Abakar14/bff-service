package com.bytmasoft.dss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class DocumentDto {
    private Long id;
    private String fileName;
    private String originalFileName;
    private String filePath;
    private Integer version;
}
