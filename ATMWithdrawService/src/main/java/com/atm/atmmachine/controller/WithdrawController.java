package com.atm.atmmachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atm.atmmachine.constants.ATMMachineConstants;
import com.atm.atmmachine.model.WithdrawRequestParam;
import com.atm.atmmachine.result.WithdrawResponse;
import com.atm.atmmachine.service.WithdrawService;

@RestController
public class WithdrawController {
	@Autowired
	private WithdrawService withdrawService;
	
	@Autowired
	private ControllerHelper controllerHelper;

	@RequestMapping(path = ATMMachineConstants.ATMMACHINE_POST_CASH_WITHDRAWAL_ENDPOINT,
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WithdrawResponse> postWithdrawMoney(
					@RequestParam("accountNumber") String accountNumber, 
					@RequestParam("pin") String pin,
					@RequestParam("requestAmount") int requestAmount) {
		
		System.out.println("In postWithdrawMoney() for accountNumber: "+accountNumber+" and requestAmount: "+requestAmount);
		
		WithdrawRequestParam inputParam = controllerHelper.createParam(accountNumber, Integer.parseInt(pin), requestAmount);
		
		WithdrawResponse requestResult = withdrawService.execute(inputParam);
		
		ResponseEntity<WithdrawResponse> response = controllerHelper.createResponse(requestResult);
		
		System.out.println("Leaving postWithdrawMoney()");
		
		return response;
	}

}
