package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.enums.CommunicationType;
import com.bytmasoft.dss.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class StudentResponseDto {

private Long id;
private Long schoolId;
private String matNumber;
private String firstName;
private String lastName;
private String birthPlace;
private Gender gender;
private String birthDate;
private String country;
private String email;
private String mobile;
private String phone;
private Long classId;
private String nameOfSchoolBefore;
private String schoolLevel;
private boolean hasCertificate;
private CommunicationType communicationType;
private String certificate;
private String chronic_illnesse;
private String doctorName;
private String doctorAddress;
private List<CourseResponseDto> courseResponseDtos;
private List<DocumentResponseDto> documentResponseDtos;
private List<GuardianResponseDto> guardianResponseDtos;
private AddressResponseDto addressResponseDto;
private LocalDateTime addedOn;
private LocalDateTime modifiedOn;
private boolean active;
private String addedBy;
private String modifiedBy;
private boolean deleted;
}
