package com.atm.atmmachine.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.constants.ATMMachineConstants.ATMMachineErrorKeys;
import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.exception.ValidationException;
import com.atm.atmmachine.manager.CustomerServiceProxy;
import com.atm.atmmachine.model.Customer;


public class CustomerAccountDaoImpl implements CustomerAccountDao {

	@Autowired
	private CustomerServiceProxy proxy;
	
	@Override
	public boolean validateCustomer(String accountNumber, int pin) {
		return findCustomer(accountNumber).getPin()==pin;
	}

	@Override
	public long fetchCustomerBalance(String accountNumber, int pin) {
		
		return findCustomer(accountNumber).getBalance();
	}

	@Override
	public void updateCustomerBalance(String accountNumber, long customerBalanceNew) {
		Customer customer =findCustomer(accountNumber);
		
		proxy.updateCustomerBalance(customer.getAccountNumber(), customer.getPin(), customerBalanceNew);
	}
	
	private Customer findCustomer(String accountNumber) {
		
		Customer customerToBeUpdated = new Customer();
		customerToBeUpdated.setAccountNumber(accountNumber);

		Optional<Customer> customer= Optional.of(proxy.findCustomer(accountNumber));
		
		 if(customer.isPresent()) 
			return customer.get();
		else
			throw new ValidationException(ATMMachineErrorKeys.BAD_REQUEST.getErrorKey());
     }
	
	
}
