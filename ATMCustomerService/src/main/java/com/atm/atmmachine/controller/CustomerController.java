package com.atm.atmmachine.controller;

import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atm.atmmachine.constants.ATMMachineConstants;
import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.param.ATMMachineParam;
import com.atm.atmmachine.result.CustomerResponse;
import com.atm.atmmachine.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService getRequestService;

	@Autowired
	private ControllerHelper controllerHelper;

	@Autowired
	private ApplicationData applicationData;

	@RequestMapping(path = ATMMachineConstants.ATMMACHINE_GET_BALANCE_ENQUIRY_ENDPOINT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponse> getBalanceEnquiry(@RequestParam("accountNumber") String accountNumber,
			@RequestParam("pin") String pin) {

		System.out.println("In getBalanceEnquiry() for: " + accountNumber);

		ATMMachineParam inputParam = controllerHelper.createParam(accountNumber, Integer.parseInt(pin));

		CustomerResponse requestResult = getRequestService.execute(inputParam);

		ResponseEntity<CustomerResponse> response = controllerHelper.createResponse(requestResult);

		System.out.println("Leaving getBalanceEnquiry()");

		return response;
	}

	@RequestMapping(path = ATMMachineConstants.ATMMACHINE_FIND_CUSTOMER_ENDPOINT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> findCustomer(@RequestParam("accountNumber") String accountNumber) {

		System.out.println("In findCustomer() for: " + accountNumber);

		Customer customerToBeUpdated = new Customer();
		customerToBeUpdated.setAccountNumber(accountNumber);

		Optional<Customer> customer = ApplicationData.getCustomerAll().stream()
				.filter(c -> c.equals(customerToBeUpdated)).findFirst();

		ResponseEntity<Customer> response = new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);

		System.out.println("Leaving findCustomer()");

		return response;
	}

	@RequestMapping(path = ATMMachineConstants.ATMMACHINE_UPDATE_CUSTOMER_BALANCE_ENDPOINT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerResponse> updateCustomerBalance(@RequestParam("accountNumber") String accountNumber,
			@RequestParam("pin") String pin, @RequestParam("amount") long requestAmount) {

		System.out.println("In updateCustomerBalance() for: " + accountNumber);

		CustomerResponse requestResult = getRequestService.executeUpdate(accountNumber, Integer.parseInt(pin), requestAmount);

		ResponseEntity<CustomerResponse> response = controllerHelper.createResponse(requestResult);
	
		System.out.println("Leaving updateCustomerBalance()");

		return response;
	}

}
