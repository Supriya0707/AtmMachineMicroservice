package com.atm.atmmachine.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.constants.ATMMachineErrorKeys;
import com.atm.atmmachine.dao.ATMMachineDao;
import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.exception.ATMMachineException;
import com.atm.atmmachine.exception.UserAccountException;
import com.atm.atmmachine.exception.ValidationException;
import com.atm.atmmachine.model.Denomination;
import com.atm.atmmachine.result.ATMMachineRequestResult;
import com.atm.atmmachine.result.CustomerResult;

public class ATMMachineRequestManager {

	@Autowired
	private EnquiryServiceProxy proxy;
	
	private CustomerAccountDao customerAccountDao;
	
	private ATMMachineDao atmMachineDao;
	
	public CustomerAccountDao getCustomerAccountDao() {
		return customerAccountDao;
	}

	@Autowired
	public void setCustomerAccountDao(CustomerAccountDao customerAccountDao) {
		this.customerAccountDao = customerAccountDao;
	}

	public ATMMachineDao getAtmMachineDao() {
		return atmMachineDao;
	}

	@Autowired
	public void setAtmMachineDao(ATMMachineDao atmMachineDao) {
		this.atmMachineDao = atmMachineDao;
	}

	public ATMMachineRequestResult fetchCustomerBalance(String accountNumber, int pin) {
		
		ATMMachineRequestResult requestResult = proxy.getBalanceEnquiry(accountNumber, pin);
		
		return requestResult;
	}

	/**
	 * Below steps will be executed in sequence, in order to withdraw money successfully.
	 * 
	 * is ATM out of service?
	 * Validate amount - in terms of ATM note value
	 * fetch customer balance
	 * check it against withdrawal amount
	 * check overdraft balance, use it if required
	 * TODO - check it against daily withdrawal limit
	 * check it against ATM balance
	 * dispense money, count notes
	 * deduct it from customer's balance
	 * deduct it from overdraft, if required
	 * TODO - put an entry in daily withdrawals
	 */
	public ATMMachineRequestResult withdrawMoney(String accountNumber,int pin,  int withdrawalAmount) {
		
		long customerBalance = 0;
		long customerBalanceNew = 0;
		int overdraftCurrent = 0;
		int overdraftNew = 0;
		ATMMachineRequestResult requestResult = null;
		long atmBalance = getAtmMachineDao().fethcATMBalance();

		if (atmBalance == 0) {
			throw new ATMMachineException(ATMMachineErrorKeys.ATM_OUT_OF_SERVICE.getErrorKey());
		}
		
		boolean isRequestedAmountValid = getAtmMachineDao().validateWithdrawalAmount(withdrawalAmount);
		if (!isRequestedAmountValid) {
			throw new ValidationException(ATMMachineErrorKeys.BAD_REQUEST_INVALID_AMOUNT.getErrorKey());
		}
		
		customerBalance = getCustomerAccountDao().fetchCustomerBalance(accountNumber, pin);
		
		if (withdrawalAmount > customerBalance) {
			overdraftCurrent = getCustomerAccountDao().fetchCustomerOverdraft(accountNumber);
			if(withdrawalAmount > (customerBalance + overdraftCurrent)) {
				throw new UserAccountException(ATMMachineErrorKeys.CUSTOMER_INSUFFICIENT_BALANCE.getErrorKey());
			}
			overdraftNew = (int) (overdraftCurrent - (withdrawalAmount - customerBalance)); 
			customerBalanceNew = 0;
			// dailyWithdrawalLimit logic also goes here
		} else {
			customerBalanceNew = customerBalance - withdrawalAmount;
			overdraftNew = overdraftCurrent;
		}
		
		if (withdrawalAmount > atmBalance) {
			throw new ATMMachineException(ATMMachineErrorKeys.ATM_INSUFFICIENT_BALANCE.getErrorKey());
		}
		
		List<Denomination> denominationResult = getAtmMachineDao().dispenseMoney(withdrawalAmount);
		
		try {
			getCustomerAccountDao().updateCustomerBalance(accountNumber, customerBalanceNew);
			if (overdraftCurrent != overdraftNew) {
				getCustomerAccountDao().updateCustomerOverdraft(accountNumber, overdraftNew);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			// TODO - Revert the ATMBalance and Denomination repository.
			throw new ATMMachineException(ATMMachineErrorKeys.ATM_MANAGED_ERROR.getErrorKey());
		}
		
		requestResult = new ATMMachineRequestResult();
		CustomerResult customerResult = new CustomerResult();
		customerResult.setAccountNumber(accountNumber);
		customerResult.setBalance(customerBalanceNew);
		// customerResult.setOverdraft(overdraftNew);		// whether to show it or not, in output
		customerResult.setWithdrawnAmount(withdrawalAmount+"");
		customerResult.setDenominationResult(denominationResult);
		requestResult.setCustomerResult(customerResult);
		
		return requestResult;
	}

}
