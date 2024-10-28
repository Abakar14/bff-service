package com.bytmasoft.dss.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "services")
public class ServicesProperties {

    private Service studentServiceStudent;
    private Service studentServiceGuardian;
    private Service studentServiceConsent;
    private Service teacherServiceTeacher;
    private Service teacherServiceCours;
    private Service schoolServiceSchool;
    private Service schoolServiceClass;
    private Service schoolServiceAddress;
    private Service schoolServiceEmployee;
    private Service storageService;

    @Getter
    @Setter
    public static class Service {
        private String name;
        private String baseUrl;
    }

}
