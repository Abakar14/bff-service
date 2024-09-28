package com.bytmasoft.dss.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BffResponseDTO {
    private StudentDetailDTO student;
    private TeacherDetailDTO teacher;
    private DocumentDTO document;
}
