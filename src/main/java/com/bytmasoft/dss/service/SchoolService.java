package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SchoolService {

private static final Logger logger = LoggerFactory.getLogger(SchoolService.class);
private final WebClientUtil webClientUtil;
private final ServicesProperties servicesProperties;


public Mono<AddressDto> saveAddress(com.bytmasoft.dss.dto.AddressCreateDto addressDto, String jwtToken) {
	System.out.println("school url : " + servicesProperties.getSchoolServiceAddress().getBaseUrl());
	System.out.printf("addressDto : " + addressDto.toString());

	return webClientUtil.post(servicesProperties.getSchoolServiceAddress().getBaseUrl(), addressDto, AddressDto.class, jwtToken);
}
}
