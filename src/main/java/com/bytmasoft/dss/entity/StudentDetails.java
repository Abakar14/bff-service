package com.bytmasoft.dss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class StudentDetails {

    private Student student;
    private List<Teacher> teachers;

}
