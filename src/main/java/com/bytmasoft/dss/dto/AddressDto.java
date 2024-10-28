package com.bytmasoft.dss.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    private Long id;
    private String city;
    private String country;
    private String street;
    private String streetNumber;
    private String zipCode;
}
