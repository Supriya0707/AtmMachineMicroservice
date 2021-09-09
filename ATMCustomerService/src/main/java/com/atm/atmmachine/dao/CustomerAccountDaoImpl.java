package com.atm.atmmachine.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.constants.ATMMachineConstants.ATMMachineErrorKeys;
import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.exception.ValidationException;
import com.atm.atmmachine.model.Customer;

public class CustomerAccountDaoImpl implements CustomerAccountDao {

	@Override
	public boolean validateCustomer(String accountNumber, int pin) {
		return findCustomer(accountNumber).getPin()==pin;
	}

	@Override
	public long fetchCustomerBalance(String accountNumber) {
		
		return findCustomer(accountNumber).getBalance();
	}

	@Override
	public void updateCustomerBalance(String accountNumber, long customerBalanceNew) {
		findCustomer(accountNumber).setBalance(customerBalanceNew);
	}
	
	private Customer findCustomer(String accountNumber) {
		
		Customer customerToBeUpdated = new Customer();
		customerToBeUpdated.setAccountNumber(accountNumber);

		Optional<Customer> customer= ApplicationData.getCustomerAll().stream().
		filter(c -> c.equals(customerToBeUpdated)).findFirst();
		 if(customer.isPresent()) 
			return customer.get();
		else
			throw new ValidationException(ATMMachineErrorKeys.BAD_REQUEST.getErrorKey());
		}
}
