package com.bytmasoft.dss.dto;

import lombok.Builder;
import lombok.Data;

//TODO must be removed

/**
 * This class must remove from here and use only
 * StudentCreateDto. Attention frontend needs to be adapted...
 */
@Deprecated
@Data
@Builder
public class StudentDetailsCreateDto {
private StudentCreateDto studentCreateDto;
private AddressCreateDto addressCreateDto;

}
