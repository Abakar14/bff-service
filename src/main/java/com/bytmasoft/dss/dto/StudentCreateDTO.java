package com.bytmasoft.dss.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

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

@Builder
public class StudentCreateDTO {

}
