package com.bytmasoft.dss.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentDetailsDto {

private StudentDto studentDto;
private AddressDto addressDto;
private List<DocumentDto> documentDtos;


}
