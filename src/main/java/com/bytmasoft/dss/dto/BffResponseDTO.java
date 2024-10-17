package com.bytmasoft.dss.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BffResponseDTO {
    private StudentDto student;
    private TeacherResponse teacher;
    private DocumentResponseDto document;
}
