package com.bytmasoft.dss.dto;


import com.bytmasoft.dss.enums.CommunicationType;
import com.bytmasoft.dss.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDto {

private Long id;
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

@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
private LocalDateTime addedOn;
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
private LocalDateTime modifiedOn;
private boolean active;
private String addedBy;
private String modifiedBy;
private boolean deleted;

}
