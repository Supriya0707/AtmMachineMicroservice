package com.atm.atmmachine.dao;

public interface CustomerAccountDao {
	
	public boolean validateCustomer(String accountNumber, int pin);

	public long fetchCustomerBalance(String accountNumber, int pin);
	
	public int fetchCustomerOverdraft(String accountNumber);
	
	public void updateCustomerBalance(String accountNumber, long customerBalanceNew);
	
	public void updateCustomerOverdraft(String accountNumber, int overdraftNew);

	
}
