package com.atm.atmmachine.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.constants.ATMMachineConstants;
import com.atm.atmmachine.constants.ATMMachineConstants.ATMMachineErrorKeys;
import com.atm.atmmachine.dao.ATMMachineDao;
import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.exception.ATMMachineException;
import com.atm.atmmachine.exception.UserAccountException;
import com.atm.atmmachine.exception.ValidationException;
import com.atm.atmmachine.model.Denomination;
import com.atm.atmmachine.result.WithdrawResponse;
import com.atm.atmmachine.result.CustomerResult;

public class WithdrawRequestManager {

	@Autowired
	private CustomerAccountDao customerAccountDao;
	
	@Autowired
	private ATMMachineDao atmMachineDao;
	

	/**
	 * Below steps will be executed in sequence, in order to withdraw money successfully.
	 * 
	 * is ATM out of service?
	 * Validate amount - in terms of ATM note value
	 * fetch customer balance
	 * check it against withdrawal amount
	 * check it against ATM balance
	 * dispense money, count notes
	 * deduct it from customer's balance
	 *
	 */
	public WithdrawResponse withdrawMoney(String accountNumber,int pin,  int withdrawalAmount) {
		
		long customerBalance = 0;
		long customerBalanceNew = 0;
		WithdrawResponse requestResult = null;
		long atmBalance = atmMachineDao.fethcATMBalance();

		// is ATM out of service?
		if (atmBalance == 0) {
			throw new ATMMachineException(ATMMachineErrorKeys.ATM_OUT_OF_SERVICE.getErrorKey());
		}
		
		//Validate amount - in terms of ATM note value
		boolean isRequestedAmountValid = atmMachineDao.validateWithdrawalAmount(withdrawalAmount);
		if (!isRequestedAmountValid) {
			throw new ValidationException(ATMMachineErrorKeys.BAD_REQUEST_INVALID_AMOUNT.getErrorKey());
		}
		
		//fetch customer balance
		customerBalance = customerAccountDao.fetchCustomerBalance(accountNumber, pin);
		
		//check it against withdrawal amount
		if (withdrawalAmount > customerBalance) {
				throw new UserAccountException(ATMMachineErrorKeys.CUSTOMER_INSUFFICIENT_BALANCE.getErrorKey());	
		} else {
			customerBalanceNew = customerBalance - withdrawalAmount;
		}
		
		//check it against ATM balance
		if (withdrawalAmount > atmBalance) {
			throw new ATMMachineException(ATMMachineErrorKeys.ATM_INSUFFICIENT_BALANCE.getErrorKey());
		}
		
		//dispense money, count notes
		List<Denomination> denominationResult = atmMachineDao.dispenseMoney(withdrawalAmount);
		
		//deduct it from customer's balance
		try {
			customerAccountDao.updateCustomerBalance(accountNumber, customerBalanceNew);
		} catch (Exception e) {
			e.printStackTrace();
			
			// TODO - Revert the ATMBalance and Denomination repository.
			throw new ATMMachineException(ATMMachineErrorKeys.ATM_MANAGED_ERROR.getErrorKey());
		}
		
		requestResult = new WithdrawResponse();
		CustomerResult customerResult = new CustomerResult();
		customerResult.setAccountNumber(accountNumber);
		customerResult.setBalance(customerBalanceNew);
		customerResult.setWithdrawnAmount(withdrawalAmount+"");
		customerResult.setDenominationResult(denominationResult);
		requestResult.setCustomerResult(customerResult);
		
		return requestResult;
	}

}
