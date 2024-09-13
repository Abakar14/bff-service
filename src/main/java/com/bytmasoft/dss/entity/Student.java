package com.bytmasoft.dss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Student {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private String fileName;
}
