package com.bytmasoft.dss.dto;

@lombok.Data
@lombok.Builder
public class GuardianCreateDto {


private String firstName;
private String lastName;
private String country;
private String email;
private String mobile;
private String phone;
private com.bytmasoft.dss.enums.Gender gender;
private com.bytmasoft.dss.enums.Relationship relationship;
private com.bytmasoft.dss.enums.ContactLevel contactLevel;
private Long addressId;
private com.bytmasoft.dss.enums.CommunicationType communicationType;


}
