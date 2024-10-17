package com.bytmasoft.dss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    StudentDto student;
    TeacherDto teacher;
    DocumentDto document;

}
