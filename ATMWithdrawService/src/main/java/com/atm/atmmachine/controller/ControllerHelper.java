package com.atm.atmmachine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.atm.atmmachine.model.WithdrawRequestParam;
import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.result.WithdrawResponse;

public class ControllerHelper {

	/**
	 * Create an object out of input parameters.
	 */
	protected WithdrawRequestParam createParam(String accountNumber, int pin) {
		
		return createParam(accountNumber, pin, 0);
	}
	
	protected WithdrawRequestParam createParam(String accountNumber, int pin, int requestAmount) {
		
		WithdrawRequestParam inputParam = new WithdrawRequestParam();
		Customer customer = new Customer();
		customer.setAccountNumber(accountNumber);
		customer.setPin(pin);
		inputParam.setCustomer(customer);
		inputParam.setWithdrawalAmount(requestAmount);
		
		return inputParam;
	}
	
	protected ResponseEntity<WithdrawResponse> createResponse(WithdrawResponse requestResult) {
		
		ResponseEntity<WithdrawResponse> response = null;
		if (requestResult.getServiceError() != null) {
			if (requestResult.getServiceError().getErrorCode().startsWith("5")) {
				response = new ResponseEntity<WithdrawResponse>(requestResult, HttpStatus.INTERNAL_SERVER_ERROR);
			} else if (requestResult.getServiceError().getErrorCode().startsWith("4")) {
				response = new ResponseEntity<WithdrawResponse>(requestResult, HttpStatus.BAD_REQUEST);
			} else if (requestResult.getServiceError().getErrorCode().startsWith("3")) {
				response = new ResponseEntity<WithdrawResponse>(requestResult, HttpStatus.UNAUTHORIZED);
			}
		} else {
			response = new ResponseEntity<WithdrawResponse>(requestResult, HttpStatus.OK);
		}
		return response;
	}
}
