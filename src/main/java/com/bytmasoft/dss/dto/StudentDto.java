package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.enums.CommunicationType;
import com.bytmasoft.dss.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  - Include more information about the student
 *    - Personal information
 *      - name, date of birth, ...etc
 *    - Academic information
 *      - matriculation number, gerade, certificateds...etc
 *    - Profile picture
 *    - Additional information
 *      - Contact details (email, phone, or address)
 *    - Class or group name
 *    - Current teachers
 * </pre>
 */
@Data
@Builder
public class StudentDto {

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
private List<Long> guardianIds;
private java.time.LocalDateTime addedOn;
private java.time.LocalDateTime modifiedOn;
private boolean active;
private String addedBy;
private String modifiedBy;
private boolean deleted;
}
