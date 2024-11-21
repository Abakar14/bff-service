package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.AddressCreateDto;
import com.bytmasoft.dss.dto.AddressResponseDto;
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


public Mono<AddressResponseDto> saveAddress(AddressCreateDto addressDto, String jwtToken) {
	System.out.println("school url : " + servicesProperties.getSchoolServiceAddress().getBaseUrl());
	System.out.printf("addressDto : " + addressDto.toString());

	return webClientUtil.post(servicesProperties.getSchoolServiceAddress().getBaseUrl(), addressDto, AddressResponseDto.class, jwtToken);
}


public Mono<AddressResponseDto> getAddress(Long id, String jwtToken) {
	System.out.println("Address url : " + servicesProperties.getSchoolServiceAddress().getBaseUrl());
	System.out.printf("addressId : " + id);
	return webClientUtil.get(servicesProperties.getSchoolServiceAddress().getBaseUrl(), AddressResponseDto.class, jwtToken);
}


}
