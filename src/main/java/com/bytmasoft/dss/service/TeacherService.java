package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.TeacherDto;
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

public Mono<TeacherDto> getTeacherById(Long teacherId, String jwtToken) {
	logger.info("Starting request to fetch teacher with ID: {}", teacherId);
	return webClientUtil.get(servicesProperties.getTeacherServiceTeacher().getBaseUrl() + teacherId, TeacherDto.class)
			       .doOnSuccess(teacher -> logger.info("Successfully fetched teacher data: {}", teacher))
			       .doOnError(throwable -> logger.error("Failed to fetch teacher for ID: {}", teacherId, throwable));
}

}


