package com.bytmasoft.dss.controller;


import com.bytmasoft.common.exception.DSSErrorResponse;
import com.bytmasoft.common.exception.controller.DSSControllerAdvice;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.security.sasl.AuthenticationException;


@org.springframework.web.bind.annotation.ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BFFControllerAdvice extends DSSControllerAdvice {

private Logger logger = LoggerFactory.getLogger(BFFControllerAdvice.class);


@ExceptionHandler(AccessDeniedException.class)
public ResponseEntity<DSSErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
	logger.error("An error occurred: {}", ex.getMessage(), ex);
	String path = request.getDescription(false); // URL and query string

	DSSErrorResponse errorResponse = customErrorResponse(HttpStatus.FORBIDDEN.value(), "Access Denied",
			"Access of student service denied " + path);
	return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
}

@ExceptionHandler(ExpiredJwtException.class)
public ResponseEntity<DSSErrorResponse> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
	logger.error("An error occurred: {}", ex.getMessage(), ex);
	String path = request.getDescription(false); // URL and query string

	DSSErrorResponse errorResponse = customErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Access Denied",
			"Access Denied token expired " + path);
	return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
}

@ExceptionHandler(AuthenticationException.class)
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public ResponseEntity<DSSErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
	logger.error("An error occurred: {}", ex.getMessage(), ex);
	String path = request.getDescription(false); // URL and query string
	DSSErrorResponse errorResponse = customErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Access Denied",
			"Access Denied token expired " + path);

	return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
}


private DSSErrorResponse customErrorResponse(int status, String error, String message) {
	return DSSErrorResponse.builder()
			       .statusCode(status)
			       .errorCode(error)
			       .message(message)
			       .build();
}

}
