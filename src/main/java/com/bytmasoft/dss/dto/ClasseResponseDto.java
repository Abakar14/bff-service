package com.bytmasoft.dss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClasseResponseDto {

private Long id;
private Boolean isActive;
private LocalDateTime addedOn;
private LocalDateTime modifiedOn;
private String addedBy;
private String modifiedBy;

private String name;
private String description;
private String icon;
private Long schoolId;


}
