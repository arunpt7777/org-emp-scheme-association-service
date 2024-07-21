package com.motta.insurance_association_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AssociationNotFoundException extends RuntimeException {

	public AssociationNotFoundException(String message) {
		super(message);
	}
}
