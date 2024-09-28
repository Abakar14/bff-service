package com.bytmasoft.dss.config;

import ch.qos.logback.core.util.FixedDelay;
import com.bytmasoft.dss.service.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class WebClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebClientUtil.class);

    private final WebClient webClient;
    private final Long fixedDelay = 3L;
    private final Long duration = 2L;


    public <T>Mono<T> get(String uri, Class<T> responseType) {
        logger.debug("Making GET request to: {}", uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType)
                .retryWhen(Retry.fixedDelay(fixedDelay, Duration.ofSeconds(duration)))
                .doOnError(throwable -> logger.error("Error during GET request: {}", throwable.getMessage(), throwable));

    }

    public <T> Mono<T> post(String uri, Object requestBody,  Class<T> responseType) {
        logger.debug("Making GET request to: {}", uri);
        return webClient.post()
                .uri(uri)
                .body(Mono.just(requestBody), Object.class)
                .retrieve()
                .bodyToMono(responseType)
                .retryWhen(Retry.fixedDelay(fixedDelay, Duration.ofSeconds(duration)))
                .doOnError(throwable -> logger.error("Error during POST request: {}", throwable.getMessage(), throwable));

    }


}
