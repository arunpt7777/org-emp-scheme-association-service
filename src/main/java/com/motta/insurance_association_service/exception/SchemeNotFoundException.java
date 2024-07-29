package com.motta.insurance_association_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SchemeNotFoundException extends RuntimeException {

	public SchemeNotFoundException(String message) {
		super(message);
	}
}
