package io.github.icesoft.catalog.infrastructure.api.rest.common.exception;

import io.github.icesoft.catalog.domain.common.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for REST API
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ProblemDetail> handleDomainException(DomainException ex, WebRequest request) {

		log.error(ex.getDetail());

		HttpStatus status = HttpStatus.valueOf(ex.getStatus());

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getDetail());
		// problemDetail.setType(URI.create("https://example.com/problems/localidad-not-found"));
		problemDetail.setTitle(ex.getTitle());

		return ResponseEntity.status(status).body(problemDetail);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex,
			WebRequest request) {

		log.error(ex.getMessage());

		HttpStatus status = HttpStatus.BAD_REQUEST;

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
		problemDetail.setTitle("Invalid argument");

		return ResponseEntity.status(status).body(problemDetail);
	}
}
