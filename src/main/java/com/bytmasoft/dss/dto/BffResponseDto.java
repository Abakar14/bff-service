package com.bytmasoft.dss.dto;

import lombok.*;

@Data
@Builder
public class BffResponseDto {
    private StudentDto student;
    private TeacherResponse teacher;
    private DocumentResponseDto document;
}
