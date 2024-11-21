package com.bytmasoft.dss.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentDetailsResponseDto {

private StudentDto studentDto;
private AddressResponseDto addressDto;
private List<DocumentDto> documentDtos;
}
