package com.bytmasoft.dss.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/*@OpenAPIDefinition(

        info = @Info(
                contact = @Contact(
                        name = "Mahamat Abakar",
                        email = "abakar61@web.de",
                        url = ""
                ),
                description = "OpenApi for BFF Service",
                title = "BFF Service",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "http://localhost"
                ),
                termsOfService = "Term of Service"

        ),
        //,
        //,
        *//* servers = {

                 @Server(
                         description = "Local ENV",
                         url = "http://localhost:8081"
                 ),
                 @Server(
                         description = "Prod ENV",
                         url = "http://www.prod:8081"
                 )
         },*//*
        security = {
                @SecurityRequirement(
                        name = "BearerAuth"
                )
        }
)
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "BearerAuth",
                        description = "Jwt authentication",
                        scheme = "Bearer",
                        type = SecuritySchemeType.HTTP,
                        bearerFormat = "JWT",
                        in = SecuritySchemeIn.HEADER
                )
        }
)*/
//@Configuration
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "BFF API", version = "v1"),
        security = @SecurityRequirement(name = "bearerAuth")  // Add Security Requirement here
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}
