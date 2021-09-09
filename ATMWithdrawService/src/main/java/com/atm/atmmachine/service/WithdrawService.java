package com.atm.atmmachine.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.helper.ErrorMessageProvider;
import com.atm.atmmachine.manager.WithdrawRequestManager;
import com.atm.atmmachine.model.WithdrawRequestParam;
import com.atm.atmmachine.result.WithdrawResponse;
import com.atm.atmmachine.result.ServiceError;
import com.atm.atmmachine.validator.Validator;

public class WithdrawService {

	@Autowired
	private Validator requestValidator;
	
	@Autowired
	private WithdrawRequestManager requestManager;
	
	@Autowired
	private ErrorMessageProvider errorMessagesProvider;
	
	

	public WithdrawResponse execute(WithdrawRequestParam inputParam) {
		
		System.out.println("In service execution");
		WithdrawResponse requestResult = null;
		
		try {
			// Do validation here
			
			requestValidator.validateCustomerAccount(inputParam.getCustomer().getAccountNumber(), inputParam.getCustomer().getPin());
			requestResult = requestManager.withdrawMoney(inputParam.getCustomer().getAccountNumber(), inputParam.getCustomer().getPin(),
															inputParam.getWithdrawalAmount());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			requestResult = prepareErrorResponse(e.getMessage());
		}
		
		System.out.println("Leaving service execution");
		
		return requestResult;
	}

	public WithdrawResponse prepareErrorResponse(String errorCode) {
		
		WithdrawResponse result = new WithdrawResponse();
		String errorMessageForKey = errorMessagesProvider.getErrorMessageForKey(errorCode);
		ServiceError serviceError = new ServiceError(errorCode, errorMessageForKey);
		result.setServiceError(serviceError);
		
		return result;
	}
}
