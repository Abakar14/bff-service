package com.bytmasoft.dss.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDetailsCreateDto {
private StudentCreateDto studentCreateDto;
private AddressCreateDto addressCreateDto;
//private GuardianCreateDto guardianCreateDto;

}
