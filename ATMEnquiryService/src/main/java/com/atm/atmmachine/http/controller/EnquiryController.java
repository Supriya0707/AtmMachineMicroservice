package com.atm.atmmachine.http.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atm.atmmachine.constants.ATMMachineEndPoints;
import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.param.ATMMachineParam;
import com.atm.atmmachine.result.ATMMachineRequestResult;
import com.atm.atmmachine.service.GetATMMachineService;

@RestController
public class EnquiryController {

	private GetATMMachineService getRequestService;

	private ControllerHelper controllerHelper;

	public GetATMMachineService getGetRequestService() {
		return getRequestService;
	}

	@Autowired
	public void setGetRequestService(GetATMMachineService getRequestService) {
		this.getRequestService = getRequestService;
	}

	public ControllerHelper getControllerHelper() {
		return controllerHelper;
	}

	@Autowired
	public void setControllerHelper(ControllerHelper controllerHelper) {
		this.controllerHelper = controllerHelper;
	}
	private ApplicationData applicationData;
	
	public ApplicationData getApplicationData() {
		return applicationData;
	}

	@Autowired
	public void setApplicationData(ApplicationData applicationData) {
		this.applicationData = applicationData;
	}
	
	@RequestMapping(path = ATMMachineEndPoints.ATMMACHINE_GET_BALANCE_ENQUIRY_ENDPOINT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ATMMachineRequestResult> getBalanceEnquiry(
			@RequestParam("accountNumber") String accountNumber, @RequestParam("pin") String pin) {

		System.out.println("In getBalanceEnquiry() for: " + accountNumber);

		ATMMachineParam inputParam = getControllerHelper().createParam(accountNumber, Integer.parseInt(pin));

		ATMMachineRequestResult requestResult = getGetRequestService().execute(inputParam);

		ResponseEntity<ATMMachineRequestResult> response = getControllerHelper().createResponse(requestResult);

		System.out.println("Leaving getBalanceEnquiry()");

		return response;
	}

	@RequestMapping(path = ATMMachineEndPoints.ATMMACHINE_fIND_CUSTOMER_ENDPOINT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> findCustomer(
			@RequestParam("accountNumber") String accountNumber) {

		System.out.println("In getBalanceEnquiry() for: " + accountNumber);

		Customer customerToBeUpdated = new Customer();
		customerToBeUpdated.setAccountNumber(accountNumber);
		
		Optional<Customer> customer =  getApplicationData().getAtmMachine().getCustomersAll().stream().
				filter(c -> c.equals(customerToBeUpdated)).findFirst();
		
		ResponseEntity<Customer> response = new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
		
		System.out.println("Leaving getBalanceEnquiry()");

		return response;
	}

}
