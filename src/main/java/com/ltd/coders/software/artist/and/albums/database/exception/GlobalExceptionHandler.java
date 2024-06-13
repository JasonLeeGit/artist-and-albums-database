package com.ltd.coders.software.artist.and.albums.database.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ InvalidDeleteParamException.class })
	public Map<String, String> handleInvalidDeleteParamException(HttpServletRequest request,
			InvalidDeleteParamException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("InvalidDeleteParamExceptionMessage", ex.getMessage());
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("ConstraintViolationExceptionMessage", ex.getMessage());
		return errorMap;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<String> handleMethodArgumentNotValidFoundException(HttpServletRequest request, MethodArgumentNotValidException ex) {
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		return errors;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ArtistNotFoundException.class)
	public Map<String, String> handleArtistNotFoundException(HttpServletRequest request, ArtistNotFoundException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("ArtistNotFoundExceptionMessage", ex.getMessage());
		return errorMap;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ArtistExistsException.class)
	public Map<String, String> handleArtistExistsException(HttpServletRequest request, ArtistExistsException ex) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("ArtistExistsExceptionMessage", ex.getMessage());
		return errorMap;
	}
}