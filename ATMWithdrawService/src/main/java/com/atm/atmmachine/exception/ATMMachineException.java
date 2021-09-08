package com.atm.atmmachine.exception;

public class ATMMachineException extends ServiceException {

	private static final long serialVersionUID = 1L;
	
	public ATMMachineException(String messageKey, Throwable t) {
		super(messageKey, t);
	}
	
	public ATMMachineException(String messageKey) {
		super(messageKey, null);
	}

}
