package com.atm.atmmachine.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.helper.ErrorMessageProvider;
import com.atm.atmmachine.manager.ATMMachineRequestManager;
import com.atm.atmmachine.param.ATMMachineParam;
import com.atm.atmmachine.result.ATMMachineRequestResult;
import com.atm.atmmachine.validator.ATMMachineRequestValidator;

public class PostATMMachineService extends AbstractService {

	private ATMMachineRequestValidator requestValidator;
	
	private ATMMachineRequestManager requestManager;
	
	private ErrorMessageProvider errorMessagesProvider;
	
	public ATMMachineRequestValidator getRequestValidator() {
		return requestValidator;
	}

	@Autowired
	public void setRequestValidator(ATMMachineRequestValidator requestValidator) {
		this.requestValidator = requestValidator;
	}

	public ATMMachineRequestManager getRequestManager() {
		return requestManager;
	}

	@Autowired
	public void setRequestManager(ATMMachineRequestManager requestManager) {
		this.requestManager = requestManager;
	}
	
	public ErrorMessageProvider getErrorMessagesProvider() {
		return errorMessagesProvider;
	}

	@Autowired
	public void setErrorMessagesProvider(ErrorMessageProvider errorMessagesProvider) {
		this.errorMessagesProvider = errorMessagesProvider;
	}

	public ATMMachineRequestResult execute(ATMMachineParam inputParam) {
		
		System.out.println("In service execution");
		ATMMachineRequestResult requestResult = null;
		
		try {
			// Do validation here
			
			getRequestValidator().validateCustomerAccount(inputParam.getCustomer().getAccountNumber(), inputParam.getCustomer().getPin());
			requestResult = getRequestManager().withdrawMoney(inputParam.getCustomer().getAccountNumber(), inputParam.getCustomer().getPin(),
															inputParam.getWithdrawalAmount());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			requestResult = prepareErrorResponse(e.getMessage());
		}
		
		System.out.println("Leaving service execution");
		
		return requestResult;
	}

}
