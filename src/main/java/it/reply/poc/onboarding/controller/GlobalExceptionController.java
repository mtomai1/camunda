package it.reply.poc.onboarding.controller;

import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.exception.OnboardingException401;
import it.reply.poc.onboarding.exception.OnboardingException404;
import it.reply.poc.onboarding.exception.OnboardingException500;
import it.reply.poc.onboarding.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse genericErrorHandler(HttpServletRequest req, Exception e) {

		log.error("", e);
		if(e.getMessage().contains("ENGINE-02006")) {
			return new ErrorResponse("ERR-00", HttpStatus.INTERNAL_SERVER_ERROR, "Invalid workflow",
											"Invalid call for current state");
		}

		return new ErrorResponse("ERR-00", HttpStatus.INTERNAL_SERVER_ERROR, "Generic Error!",
										"A generic error occurred, please contact support");
	}

	@ExceptionHandler(OnboardingException400.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse genericErrorHandler(HttpServletRequest req, OnboardingException400 e) {

		log.error("", e);
		return new ErrorResponse("BAD-00", HttpStatus.BAD_REQUEST, "Invalid Request!",
										e.getExceptionMessage());
	}

	@ExceptionHandler(OnboardingException401.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorResponse genericErrorHandler(HttpServletRequest req, OnboardingException401 e) {

		log.error("", e);
		return new ErrorResponse("AUTH-00", HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(),
										e.getExceptionMessage());
	}

	@ExceptionHandler(OnboardingException500.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse genericErrorHandler(HttpServletRequest req, OnboardingException500 e) {

		log.error("", e);
		return new ErrorResponse("ERR-01", HttpStatus.INTERNAL_SERVER_ERROR, "Ops.. Something gone wrong!",
										e.getExceptionMessage());
	}

	@ExceptionHandler(OnboardingException404.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse genericErrorHandler(HttpServletRequest req, OnboardingException404 e) {

		log.error("", e);
		return new ErrorResponse("ERR-02", HttpStatus.NOT_FOUND, e.getMessage(),
										"The inserted user doesn't have a state-machine associated");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse validationErrorHandler(HttpServletRequest req, Exception e) {

		log.error("", e);
		return new ErrorResponse("REQ-00", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
										"Invalid JSON format.");
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse httpMediaTypeNotSupportedException(HttpServletRequest req, Exception e) {

		log.error("", e);
		return new ErrorResponse("REQ-01", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
										"Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported");
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse messageNotReadableException(HttpServletRequest req, Exception e) {

		log.error("", e);
		return new ErrorResponse("REQ-02", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
										"Unexpected end-of-input: expected close marker for Object");
	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse httpMediaTypeNotAcceptableException(HttpServletRequest req, Exception e) {

		log.error("", e);
		return new ErrorResponse("REQ-03", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
										"Could not find acceptable representation");
	}

}
