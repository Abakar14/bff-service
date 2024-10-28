package com.bytmasoft.dss.dto;


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
public class StudentUpdateDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean active;
    private String profilePictureUrl;
    private String gender;
    private String gpa;

}
