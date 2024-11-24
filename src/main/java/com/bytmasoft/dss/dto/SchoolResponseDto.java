package com.bytmasoft.dss.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolResponseDto {

private Long id;
private String name;
private String description;
private String email;
private String phone;
private String website;
private AddressResponseDto addressResponseDto;

}
