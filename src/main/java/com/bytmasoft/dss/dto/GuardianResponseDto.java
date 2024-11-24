package com.bytmasoft.dss.dto;

import com.bytmasoft.dss.enums.CommunicationType;
import com.bytmasoft.dss.enums.ContactLevel;
import com.bytmasoft.dss.enums.Gender;
import com.bytmasoft.dss.enums.Relationship;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuardianResponseDto {

private Long id;
private Long schoolId;
private String firstName;
private String lastName;
private String country;
private String email;
private String mobile;
private String phone;
private Gender gender;
private Relationship relationship;
private ContactLevel contactLevel;
private Long addressId;
private CommunicationType communicationType;
//  private List<Long> documentsIds;
 /*   private List<Long> constentIds = new ArrayList<>();
    private List<Long> studentIds = new ArrayList<>(); */

}
