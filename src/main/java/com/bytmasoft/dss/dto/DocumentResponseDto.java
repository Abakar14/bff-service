package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.enums.DocumentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DocumentResponseDto {
private Long id;
private Long ownerId;
private String fileName;
private String filePath;
private Integer version;
private String addedBy;
private String modifiedBy;
private DocumentType documentType;
private LocalDateTime addedOn;
private LocalDateTime modifiedOn;
private String originalFileName;
private boolean deleted;
private boolean isArchived;
}
