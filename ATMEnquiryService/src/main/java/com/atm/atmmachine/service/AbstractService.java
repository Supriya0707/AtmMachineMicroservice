package com.atm.atmmachine.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.helper.ErrorMessageProvider;
import com.atm.atmmachine.result.ATMMachineRequestResult;
import com.atm.atmmachine.result.ServiceError;

public abstract class AbstractService {

	private ErrorMessageProvider errorMessagesProvider;
	
	public ErrorMessageProvider getErrorMessagesProvider() {
		return errorMessagesProvider;
	}

	@Autowired
	public void setErrorMessagesProvider(ErrorMessageProvider errorMessagesProvider) {
		this.errorMessagesProvider = errorMessagesProvider;
	}

	public ATMMachineRequestResult prepareErrorResponse(String errorCode) {
		
		ATMMachineRequestResult result = new ATMMachineRequestResult();
		String errorMessageForKey = getErrorMessagesProvider().getErrorMessageForKey(errorCode);
		ServiceError serviceError = new ServiceError(errorCode, errorMessageForKey);
		result.setServiceError(serviceError);
		
		return result;
	}
}
