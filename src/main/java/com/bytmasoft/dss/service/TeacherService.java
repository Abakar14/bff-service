package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.StudentResponseDto;
import com.bytmasoft.dss.dto.TeacherCreateDto;
import com.bytmasoft.dss.dto.TeacherResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class TeacherService {

private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);
private final WebClientUtil webClientUtil;
private final ServicesProperties servicesProperties;


public Mono<TeacherResponseDto> saveTeacher(TeacherCreateDto teacherCreateDto, String jwtToken) {
	System.out.println("Teacher url : " + servicesProperties.getTeacherServiceTeacher().getBaseUrl());
	System.out.printf("teacherCreateDto : " + teacherCreateDto.toString());

	return webClientUtil.post(servicesProperties.getTeacherServiceTeacher().getBaseUrl(), teacherCreateDto, TeacherResponseDto.class, jwtToken);
}


public Mono<TeacherResponseDto> getTeacher(Long id, String jwtToken) {
	System.out.println("Address url : " + servicesProperties.getTeacherServiceTeacher().getBaseUrl());
	System.out.printf("addressId : " + id);
	return webClientUtil.get(servicesProperties.getTeacherServiceTeacher().getBaseUrl(), TeacherResponseDto.class, jwtToken);
}


public Mono<TeacherResponseDto> getTeachers(StudentResponseDto studentResponseDto, String jwtToken) {

	return webClientUtil.get(servicesProperties.getTeacherServiceTeacher().getBaseUrl(), TeacherResponseDto.class, jwtToken);
}
}
