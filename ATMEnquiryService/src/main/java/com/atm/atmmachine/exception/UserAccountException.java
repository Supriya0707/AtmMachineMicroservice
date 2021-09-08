package com.atm.atmmachine.exception;

public class UserAccountException extends ServiceException {

	private static final long serialVersionUID = 1L;
	
	public UserAccountException(String messageKey, Throwable t) {
		super(messageKey, t);
	}
	
	public UserAccountException(String messageKey) {
		super(messageKey, null);
	}

}
