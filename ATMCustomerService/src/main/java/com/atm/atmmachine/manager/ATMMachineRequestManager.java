package com.atm.atmmachine.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.result.CustomerResponse;
import com.atm.atmmachine.result.CustomerResult;

public class ATMMachineRequestManager {
	@Autowired
	private CustomerAccountDao customerAccountDao;

	public CustomerResponse fetchCustomerBalance(String accountNumber) {
		
		long balance = customerAccountDao.fetchCustomerBalance(accountNumber);
		
		CustomerResponse requestResult = new CustomerResponse();
		CustomerResult customerResult = new CustomerResult();
		customerResult.setAccountNumber(accountNumber);
		customerResult.setBalance(balance);
		requestResult.setCustomerResult(customerResult);
		
		return requestResult;
	}

}
