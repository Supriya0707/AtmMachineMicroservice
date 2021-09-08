package com.atm.atmmachine.exception;

public class ValidationException extends ServiceException {

	private static final long serialVersionUID = 1L;
	
	public ValidationException(String messageKey, Throwable t) {
		super(messageKey, t);
	}

	public ValidationException(String messageKey) {
		super(messageKey, null);
	}

}
