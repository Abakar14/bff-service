package com.bytmasoft.dss.service;

import com.bytmasoft.dss.config.ServicesProperties;
import com.bytmasoft.dss.config.WebClientUtil;
import com.bytmasoft.dss.dto.AddressCreateDto;
import com.bytmasoft.dss.dto.AddressResponseDto;
import com.bytmasoft.dss.dto.SchoolResponseDto;
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


public Mono<AddressResponseDto> getAddress(Long addressId, String jwtToken) {
	String uri = String.format("%s/%d", servicesProperties.getSchoolServiceAddress().getBaseUrl(), addressId);

	return webClientUtil.fetchOne(uri, AddressResponseDto.class, jwtToken);
}


public Mono<SchoolResponseDto> getSchool(Long schoolId, String jwtToken) {
	String uri = String.format("%s/%d", servicesProperties.getSchoolServiceSchool().getBaseUrl(), schoolId);

	return webClientUtil.fetchOne(uri, SchoolResponseDto.class, jwtToken);

}


}
