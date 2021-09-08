package com.atm.atmmachine.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atm.atmmachine.constants.ATMMachineEndPoints;
import com.atm.atmmachine.param.ATMMachineParam;
import com.atm.atmmachine.result.ATMMachineRequestResult;
import com.atm.atmmachine.service.PostATMMachineService;

@RestController
public class WithdrawController {

	private PostATMMachineService postRequestService;
	
	private ControllerHelper controllerHelper;
	
	public PostATMMachineService getPostRequestService() {
		return postRequestService;
	}

	@Autowired
	public void setPostRequestService(PostATMMachineService postRequestService) {
		this.postRequestService = postRequestService;
	}
	
	public ControllerHelper getControllerHelper() {
		return controllerHelper;
	}

	@Autowired
	public void setControllerHelper(ControllerHelper controllerHelper) {
		this.controllerHelper = controllerHelper;
	}

	@RequestMapping(path = ATMMachineEndPoints.ATMMACHINE_POST_CASH_WITHDRAWAL_ENDPOINT,
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ATMMachineRequestResult> postWithdrawMoney(
					@RequestParam("accountNumber") String accountNumber, 
					@RequestParam("pin") String pin,
					@RequestParam("requestAmount") int requestAmount) {
		
		System.out.println("In postWithdrawMoney() for accountNumber: "+accountNumber+" and requestAmount: "+requestAmount);
		
		ATMMachineParam inputParam = getControllerHelper().createParam(accountNumber, Integer.parseInt(pin), requestAmount);
		
		ATMMachineRequestResult requestResult = getPostRequestService().execute(inputParam);
		
		ResponseEntity<ATMMachineRequestResult> response = getControllerHelper().createResponse(requestResult);
		
		System.out.println("Leaving postWithdrawMoney()");
		
		return response;
	}

}
