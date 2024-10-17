package com.bytmasoft.dss.security;

import com.bytmasoft.dss.security.exception.JwtAuthenticationEntryPoint;
import com.bytmasoft.dss.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BffSecurityConfig {

    private static final String[] SERVICE_WHITE_LIST_URL = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"

    };
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;



    public BffSecurityConfig(JwtUtil jwtUtil, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtUtil = jwtUtil;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(SERVICE_WHITE_LIST_URL).permitAll();
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); // Allow preflight requests
                    auth.anyRequest().authenticated();
                })

                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define a password encoder
    }

/*    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurer() {
        CorsConfiguration corsCon = new CorsConfiguration();
        corsCon.setAllowedOrigins(List.of("http://localhost:4200"));
        corsCon.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsCon.addAllowedHeader("Authorization");
        corsCon.addAllowedHeader("Content-Type");
        corsCon.setAllowedHeaders(List.of("*"));
        corsCon.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsCon);
        return source;
    }*/

}
