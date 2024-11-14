package com.bytmasoft.dss.dto;

@lombok.Data
@lombok.Builder
public class AddressCreateDto {
private String city;
private String country;
private String street;
private String streetNumber;
private String zipCode;
}
