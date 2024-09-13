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

    private Service studentService;
    private Service teacherService;

    @Getter
    @Setter
    public static class Service {
        private String name;
        private String baseUrl;
    }

}
