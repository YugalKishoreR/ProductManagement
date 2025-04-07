package com.example.ProductManagement.exception;

public class ProductException extends RuntimeException {

	private final String message;

	public ProductException(String message) {
		this.message = message;

	}

	public String getMessage() {
		return message;
	}

}
