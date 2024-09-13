package com.bytmasoft.dss.service;

import com.bytmasoft.dss.entity.StudentDetails;

public interface BFFService {
    StudentDetails getStudentDetails(Long studentId,  String authorizationHeader);
}
