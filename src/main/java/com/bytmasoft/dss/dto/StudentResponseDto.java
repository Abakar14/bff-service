package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.enums.CommunicationType;
import com.bytmasoft.dss.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
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
private Boolean isActive;
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
private LocalDateTime addedOn;
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
private LocalDateTime modifiedOn;
private String modifiedBy;
private String addedBy;

private Long schoolId;
private String matNumber;
private String firstName;
private String lastName;
private String birthPlace;
private String birthDate;
private Gender gender;
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
private List<Long> guardianIds; // List of Guardian IDs instead of full objects
private boolean deleted;

private ClasseResponseDto classeResponseDto;
private List<CourseResponseDto> courseResponseDtos;
private List<DocumentResponseDto> documentResponseDtos;
private List<GuardianResponseDto> guardianResponseDtos;
private AddressResponseDto addressResponseDto;

}
