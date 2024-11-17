package com.bytmasoft.dss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressDto {

private Long id;
private String city;
private String country;
private String street;
private String streetNumber;
private String zipCode;
}
