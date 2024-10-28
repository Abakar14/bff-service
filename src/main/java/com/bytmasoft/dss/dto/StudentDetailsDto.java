package com.bytmasoft.dss.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDetailsDto {

    private StudentDto student;
    private AddressDto addressDto;
    private GuardianDto guardianDto;
    private DocumentDto documentDto;

}
