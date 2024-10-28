package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.enums.CommunicationType;
import com.bytmasoft.dss.enums.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
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

    @NotNull
    private Long id;
    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;
    @NotNull
    @Size(min = 3, max = 50)
    private String placeOfBirth;

//    @NotNull(message = "Gender is required")
    private Gender gender;
    private Long addressId;
//    @NotNull(message = "Birthday is required")
    private String birthday; // Accept birthday as a string in a formatted way
    private String mobile;
    private String country;
  /*  private String email;
    private String mobile;
    private String phone;*/

    private Long schoolId;
    private Long cuurentTeacherId;
    private Long classId;


    private String nameOfSchoolBefore;
    private String schoolLevel;
    private boolean hasCertificate;
    private CommunicationType communicationType;
    private String certificate;
    private String chronic_illnesse;
    private String doctorName;
    private String doctorAddress;
    private List<Long> documentsIds;
    private List<Long> guardianIds = new ArrayList<>();




}
