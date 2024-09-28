package com.bytmasoft.dss.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class WebClientErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebClientErrorHandler.class);

    public static <T> Mono<T> handleError(ClientResponse response) {
        HttpStatus status = (HttpStatus) response.statusCode();

        if (status.is4xxClientError()) {
            logger.error("4xx error occurred: " + status);
        } else if (status.is5xxServerError()) {
            logger.error("5xx error occurred: " + status);
        }

        return response.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new WebClientResponseException(
                        "Error response: " + errorBody, status.value(), status.getReasonPhrase(), null, null, null
                )));
    }
}
