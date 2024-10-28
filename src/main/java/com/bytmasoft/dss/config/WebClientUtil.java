package com.bytmasoft.dss.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WebClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebClientUtil.class);

    private final WebClient webClient;
    private final Long fixedDelay = 3L;
    private final Long duration = 2L;
    private final ObjectPostProcessor objectPostProcessor;
    private final GenericResponseService responseBuilder;


    public <T>Mono<T> get(String uri, Class<T> responseType) {
        logger.debug("Making GET request to: {}", uri);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType)
                .retryWhen(Retry.fixedDelay(fixedDelay, Duration.ofSeconds(duration)))
                .doOnError(throwable -> logger.error("Error during GET request: {}", throwable.getMessage(), throwable));

    }

    public <T> Mono<T> post(String uri, Object requestBody,  Class<T> responseType, String jwtToken) {
        logger.debug("Making GET request to: {}", uri);
        return webClient.post()
                .uri(uri)
                .header("Authorization", jwtToken)
                .body(Mono.just(requestBody), Object.class)
                .retrieve()
                .bodyToMono(responseType)
                .retryWhen(Retry.fixedDelay(fixedDelay, Duration.ofSeconds(duration)))
                .doOnError(throwable -> logger.error("Error during POST request: {}", throwable.getMessage(), throwable));

    }


    public <T, R>Mono<List<R>> saveEntities(String uri, T requestBody, Class<R> responseType, String jwtToken){
        logger.debug("Making post request to: {}", uri);
        return webClient
                .post()
                .header("Authorization", jwtToken)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(responseType)
                .collectList();

    }

    public <T> Flux<T> postList(String uri, Object requestBody, Class<T> responseType, String jwtToken) {
        logger.debug("Making GET request to: {}", uri);
        return webClient.post()
                .uri(uri)
                .header("Authorization", jwtToken)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(responseType)
                .retryWhen(Retry.fixedDelay(fixedDelay, Duration.ofSeconds(duration)))
                .doOnError(throwable -> logger.error("Error during POST request: {}", throwable.getMessage(), throwable));

    }


}
