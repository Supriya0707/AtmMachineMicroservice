package com.atm.atmmachine.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.result.ATMMachineRequestResult;
import com.atm.atmmachine.result.CustomerResult;

public class ATMMachineRequestManager {

	private CustomerAccountDao customerAccountDao;
	
	public CustomerAccountDao getCustomerAccountDao() {
		return customerAccountDao;
	}

	@Autowired
	public void setCustomerAccountDao(CustomerAccountDao customerAccountDao) {
		this.customerAccountDao = customerAccountDao;
	}

	public ATMMachineRequestResult fetchCustomerBalance(String accountNumber) {
		
		long balance = getCustomerAccountDao().fetchCustomerBalance(accountNumber);
		
		ATMMachineRequestResult requestResult = new ATMMachineRequestResult();
		CustomerResult customerResult = new CustomerResult();
		customerResult.setAccountNumber(accountNumber);
		customerResult.setBalance(balance);
		requestResult.setCustomerResult(customerResult);
		
		return requestResult;
	}

}
