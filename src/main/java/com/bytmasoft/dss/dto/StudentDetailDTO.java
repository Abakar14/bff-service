package com.bytmasoft.dss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

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
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StudentDetailDTO extends StudentDetailCreateDTO implements Serializable {

    private String fileName;
    private String addedOn;
    private String modifiedOn;
    private String matNumber;
    private String insertedBy;
    private Date dateOfBirth;



}
