package com.example.ProductManagement.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public String handleProductNotFoundException(CustomException ex) {
		return "Error: " + ex.getMessage();
	}

}
