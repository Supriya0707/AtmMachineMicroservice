package com.atm.atmmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Customer {

	@JsonProperty("accountNumber")
	private String accountNumber;
	
	@JsonProperty("pin")
	private int pin;
	
	@JsonProperty("balance")
	private long balance;
	
	// @JsonProperty("overdraft")
	@JsonProperty("overdraft")
	private int overdraft;
	
	
	// private int dailyWithdrawalLimit;
	
	public Customer() {
	}
	
	public Customer(String accountNumber, int pin, int balance, int overdraft) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = balance;
		this.overdraft = overdraft;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	
	public int getOverdraft() {
		return overdraft;
	}
	public void setOverdraft(int overdraft) {
		this.overdraft = overdraft;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [accountNumber=" + accountNumber + ", balance=" + balance + ", overdraft=" + overdraft + "]";
	}
	
}
