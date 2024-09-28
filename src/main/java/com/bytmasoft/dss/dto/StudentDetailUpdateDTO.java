package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.entity.Address;
import com.bytmasoft.dss.entity.Course;
import com.bytmasoft.dss.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class StudentDetailUpdateDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean active;
    private String profilePictureUrl;
    private String gender;
    private String gpa;
    private Address address;
    private List<Course> courses;
    private List<Teacher> teachers;
}
