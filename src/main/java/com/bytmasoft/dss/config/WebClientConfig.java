package com.bytmasoft.dss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

//    @Bean
//    @ConditionalOnMissingBean
//    public WebClient webClient() {
//        return WebClient.builder()
//                .exchangeStrategies(ExchangeStrategies.builder()
//                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build())
//                .baseUrl(url).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
//    }
}
