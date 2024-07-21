package com.motta.insurance_association_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class AssociationAlreadyExistsException extends RuntimeException {

	public AssociationAlreadyExistsException(String message) {
		super(message);
	}
}
