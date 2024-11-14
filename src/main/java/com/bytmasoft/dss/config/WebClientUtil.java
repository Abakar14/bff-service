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


public <T> Mono<T> get(String uri, Class<T> responseType) {
	logger.debug("Making GET request to: {}", uri);
	return webClient.get()
			       .uri(uri)
			       .retrieve()
			       .bodyToMono(responseType)
			       .retryWhen(Retry.fixedDelay(fixedDelay, Duration.ofSeconds(duration)))
			       .doOnError(throwable -> logger.error("Error during GET request: {}", throwable.getMessage(), throwable));

}


public <T, R> Mono<R> saveEntity(String uri, T requestBody, Class<R> responseType, String jwtToken) {
	logger.info("Making POST request to: {}", uri);
	logger.debug("Request Body: {}", requestBody);

	System.out.println("Making post request to: " + uri + " JWT " + jwtToken);
	System.out.println("requestBody : " + requestBody.toString());
	System.out.println("responseType " + responseType.toString());

	return webClient
			       .post()
			       .uri(uri)
			       .header("Authorization", jwtToken)
			       .header("Content-Type", "application/json")
			       .header("accept", "application/json")
			       .body(Mono.just(requestBody), (Class<T>) requestBody.getClass())
			       .exchangeToMono(response -> {
				       if (response.statusCode().is2xxSuccessful()) {
					       return response.bodyToMono(responseType);
				       } else {
					       return response.bodyToMono(String.class)
							              .doOnNext(errorBody -> logger.error("Error response body: {}", errorBody))
							              .flatMap(errorBody -> response.createException()
									                                    .flatMap(exception -> Mono.error(new RuntimeException("Error response: " + errorBody, exception))));
				       }
			       })
			       .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
			       .doOnError(throwable -> logger.error("Error during POST request: {}", throwable.getMessage()));
}

public <T> Mono<T> post(String uri, Object requestBody, Class<T> responseType, String jwtToken) {
	logger.debug("Making GET request to: {}", uri);
	if (requestBody == null) {
		System.out.printf("requestBody is null");
	}
	return webClient.post()
			       .uri(uri)
			       .header("Authorization", jwtToken)
			       .body(Mono.just(requestBody), Object.class)
			       .retrieve()
			       .bodyToMono(responseType)
			       .retryWhen(Retry.fixedDelay(fixedDelay, Duration.ofSeconds(duration)))
			       .doOnError(throwable -> logger.error("Error during POST request: {}", throwable.getMessage(), throwable));

}


public <T, R> Mono<List<R>> saveEntities(String uri, T requestBody, Class<R> responseType, String jwtToken) {
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
