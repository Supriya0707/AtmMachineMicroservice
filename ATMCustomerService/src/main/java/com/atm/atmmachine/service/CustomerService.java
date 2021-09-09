package com.atm.atmmachine.service;

import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.atm.atmmachine.constants.ATMMachineConstants.ATMMachineErrorKeys;
import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.helper.ErrorMessageProvider;
import com.atm.atmmachine.manager.ATMMachineRequestManager;
import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.param.ATMMachineParam;
import com.atm.atmmachine.result.CustomerResponse;
import com.atm.atmmachine.result.CustomerResult;
import com.atm.atmmachine.result.ServiceError;
import com.atm.atmmachine.validator.Validator;

public class CustomerService {

	@Autowired
	private Validator requestValidator;
	
	@Autowired
	private ATMMachineRequestManager requestManager;
	
	@Autowired
	private ErrorMessageProvider errorMessagesProvider;
	

	public CustomerResponse execute(ATMMachineParam inputParam) {
		
		System.out.println("In service execution");
		CustomerResponse requestResult = null;
		
		try {
			
			requestValidator.validateCustomerAccount(inputParam.getCustomer().getAccountNumber(), inputParam.getCustomer().getPin());
			requestResult = requestManager.fetchCustomerBalance(inputParam.getCustomer().getAccountNumber());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			requestResult = prepareErrorResponse(e.getMessage());
		}
		System.out.println("Leaving service execution");
		
		return requestResult;
	}

	public CustomerResponse prepareErrorResponse(String errorCode) {
		
		CustomerResponse result = new CustomerResponse();
		String errorMessageForKey = errorMessagesProvider.getErrorMessageForKey(errorCode);
		ServiceError serviceError = new ServiceError(errorCode, errorMessageForKey);
		result.setServiceError(serviceError);
		
		return result;
	}

	public CustomerResponse executeUpdate(String accountNumber,int pin,long requestAmount) {
		
		
		System.out.println("In service update execution");
		CustomerResponse requestResult = new CustomerResponse();;
		
		try {
			
			requestValidator.validateCustomerAccount(accountNumber, pin);

			Customer customerToBeUpdated = new Customer();
			customerToBeUpdated.setAccountNumber(accountNumber);

			Optional<Customer> customer = ApplicationData.getCustomerAll().stream().filter(c -> c.equals(customerToBeUpdated))
					.findFirst();

			if (customer.isPresent()) {
				customer.get().setBalance(requestAmount);
			} else {
				throw new ValidationException(ATMMachineErrorKeys.BAD_REQUEST.getErrorKey());
			}
			
			CustomerResult customerResult = new CustomerResult();
			customerResult.setAccountNumber(accountNumber);
			customerResult.setBalance(requestAmount);
			requestResult.setCustomerResult(customerResult);
			
		} catch (Exception e) {
			e.printStackTrace();
			requestResult = prepareErrorResponse(e.getMessage());
		}
		System.out.println("Leaving service update execution");
		
		return requestResult;
	}
	
}
