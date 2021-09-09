package com.atm.atmmachine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.param.ATMMachineParam;
import com.atm.atmmachine.result.CustomerResponse;

public class ControllerHelper {

	/**
	 * Create an object out of input parameters.
	 */
	protected ATMMachineParam createParam(String accountNumber, int pin) {
		
		return createParam(accountNumber, pin, 0);
	}
	
	protected ATMMachineParam createParam(String accountNumber, int pin, int requestAmount) {
		
		ATMMachineParam inputParam = new ATMMachineParam();
		Customer customer = new Customer();
		customer.setAccountNumber(accountNumber);
		customer.setPin(pin);
		inputParam.setCustomer(customer);
		inputParam.setWithdrawalAmount(requestAmount);
		
		return inputParam;
	}
	
	protected ResponseEntity<CustomerResponse> createResponse(CustomerResponse requestResult) {
		
		ResponseEntity<CustomerResponse> response = null;
		if (requestResult.getServiceError() != null) {
			if (requestResult.getServiceError().getErrorCode().startsWith("5")) {
				response = new ResponseEntity<CustomerResponse>(requestResult, HttpStatus.INTERNAL_SERVER_ERROR);
			} else if (requestResult.getServiceError().getErrorCode().startsWith("4")) {
				response = new ResponseEntity<CustomerResponse>(requestResult, HttpStatus.BAD_REQUEST);
			} else if (requestResult.getServiceError().getErrorCode().startsWith("3")) {
				response = new ResponseEntity<CustomerResponse>(requestResult, HttpStatus.UNAUTHORIZED);
			}
		} else {
			response = new ResponseEntity<CustomerResponse>(requestResult, HttpStatus.OK);
		}
		return response;
	}
}
