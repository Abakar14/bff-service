package com.bytmasoft.dss.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentDetailsResponseDto {

private StudentResponseDto studentResponseDto;
private AddressResponseDto addressResponseDto;
private List<DocumentDto> documentDtos;
}
