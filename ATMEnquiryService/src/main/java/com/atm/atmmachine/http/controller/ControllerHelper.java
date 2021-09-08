package com.atm.atmmachine.http.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.param.ATMMachineParam;
import com.atm.atmmachine.result.ATMMachineRequestResult;

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
	
	protected ResponseEntity<ATMMachineRequestResult> createResponse(ATMMachineRequestResult requestResult) {
		
		ResponseEntity<ATMMachineRequestResult> response = null;
		if (requestResult.getServiceError() != null) {
			if (requestResult.getServiceError().getErrorCode().startsWith("1")) {
				response = new ResponseEntity<ATMMachineRequestResult>(requestResult, HttpStatus.INTERNAL_SERVER_ERROR);
			} else if (requestResult.getServiceError().getErrorCode().startsWith("2")) {
				response = new ResponseEntity<ATMMachineRequestResult>(requestResult, HttpStatus.BAD_REQUEST);
			} else if (requestResult.getServiceError().getErrorCode().startsWith("3")) {
				response = new ResponseEntity<ATMMachineRequestResult>(requestResult, HttpStatus.UNAUTHORIZED);
			}
		} else {
			response = new ResponseEntity<ATMMachineRequestResult>(requestResult, HttpStatus.OK);
		}
		return response;
	}
}
