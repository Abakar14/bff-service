package com.bytmasoft.dss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Teacher {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String fileName;
    private List<Course> courses;
}
