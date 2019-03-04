package com.demo.bankapp.exception.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.bankapp.exception.BadCredentialsException;
import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.exception.DailyOperationLimitReachedException;
import com.demo.bankapp.exception.InsufficientFundsException;
import com.demo.bankapp.exception.TransactionLimitException;
import com.demo.bankapp.exception.UserNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
		return buildResponseEntity(ex, apiError);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return buildResponseEntity(ex, apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
		return buildResponseEntity(ex, apiError);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return buildResponseEntity(ex, apiError);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
		final List<String> errors = new ArrayList<>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
		}

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return buildResponseEntity(ex, apiError);
	}

	// Custom Exception Handlers -> Add next ones after these methods.

	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<Object> handleBadCredentials(final BadCredentialsException ex, final WebRequest request) {
		final ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), "error occurred");
		return buildResponseEntity(ex, apiError);
	}

	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<Object> handleBadRequest(final BadRequestException ex, final WebRequest request) {
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "error occurred");
		return buildResponseEntity(ex, apiError);
	}

	@ExceptionHandler({ UserNotFoundException.class })
	public ResponseEntity<Object> handleUserNotFound(final UserNotFoundException ex, final WebRequest request) {
		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "error occurred");
		return buildResponseEntity(ex, apiError);
	}

	@ExceptionHandler({ InsufficientFundsException.class })
	public ResponseEntity<Object> handle(final InsufficientFundsException ex, final WebRequest request) {
		final ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), "error occurred");
		return buildResponseEntity(ex, apiError);
	}

	@ExceptionHandler({ TransactionLimitException.class })
	public ResponseEntity<Object> handle(final TransactionLimitException ex, final WebRequest request) {
		final ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), "error occurred");
		return buildResponseEntity(ex, apiError);
	}

	@ExceptionHandler({ DailyOperationLimitReachedException.class })
	public ResponseEntity<Object> handle(final DailyOperationLimitReachedException ex, final WebRequest request) {
		final ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), "error occurred");
		return buildResponseEntity(ex, apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(Exception ex, ApiError apiError) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
