package com.bytmasoft.dss.dto;


import com.bytmasoft.dss.enums.CommunicationType;
import com.bytmasoft.dss.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentCreateDto {

private String firstName;

private String lastName;

private String birthPlace;

private Gender gender;

private String birthDate;
private Long schoolId;
private String country;
private String email;
private String mobile;
private String phone;
private Long addressId;
private Long classId;
private String nameOfSchoolBefore;
private String schoolLevel;
private boolean hasCertificate;
private CommunicationType communicationType;
private String certificate;
private String chronic_illnesse;
private String doctorName;
private String doctorAddress;
private List<Long> courseIds;
private List<Long> documentsIds;
private List<GuardianCreateDto> guardianCreateDtos;

}
