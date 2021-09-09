package com.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.atm.atmmachine.dao.ATMMachineDao;
import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.manager.WithdrawRequestManager;
import com.atm.atmmachine.model.Denomination;
import com.atm.atmmachine.result.WithdrawResponse;
import com.atm.atmmachine.result.CustomerResult;

@SpringBootTest
class AtmWithdrawServiceApplicationTests {

    
	@InjectMocks
	private WithdrawRequestManager atmMachineRequestManager;
	
	@Mock
	private CustomerAccountDao customerAccountDao;
	
	@Mock
	private ATMMachineDao atmMachineDao;
	
	
	@Test
	public void test_withdrawMoney_validInputsCustomerHasSufficientBalance() {
		
		long atmBalance = 1000;
		String inputAccountNumber = "111111111";
		int pin = 1234;
		int withdrawalAmount = 400;
		long customerBalance = 900;
		long customerBalanceNew = 500;
		List<Denomination> denominationResult = TestUtility.getDenominationResult();
		
		Mockito.when(atmMachineDao.fethcATMBalance()).thenReturn(atmBalance);
		Mockito.when(atmMachineDao.validateWithdrawalAmount(withdrawalAmount)).thenReturn(Boolean.TRUE);
		Mockito.when(customerAccountDao.fetchCustomerBalance(inputAccountNumber,pin)).thenReturn(customerBalance);
		Mockito.when(atmMachineDao.dispenseMoney(withdrawalAmount)).thenReturn(denominationResult);
		Mockito.doNothing().when(customerAccountDao).updateCustomerBalance(inputAccountNumber, customerBalanceNew);
		
		WithdrawResponse requestResult = atmMachineRequestManager.withdrawMoney(inputAccountNumber,pin, withdrawalAmount);
		CustomerResult customer = requestResult.getCustomerResult();
		
		Mockito.verify(customerAccountDao).fetchCustomerBalance(inputAccountNumber,pin);
		Mockito.verify(customerAccountDao).updateCustomerBalance(inputAccountNumber, customerBalanceNew);
		Mockito.verify(atmMachineDao).fethcATMBalance();
		Mockito.verify(atmMachineDao).validateWithdrawalAmount(withdrawalAmount);
		Mockito.verify(atmMachineDao).dispenseMoney(withdrawalAmount);
		assertEquals(inputAccountNumber, customer.getAccountNumber());
		assertEquals(customerBalanceNew, customer.getBalance());
		assertEquals(withdrawalAmount+"", customer.getWithdrawnAmount());
		assertEquals(denominationResult, customer.getDenominationResult());
	}
	
	@Test
	public void test_withdrawMoney_InvalidInputCustomerInvalid() {
		
		long atmBalance = 1000;
		String inputAccountNumber = "111111111";
		int pin = 1234;
		int withdrawalAmount = 100;
		
		Mockito.when(atmMachineDao.fethcATMBalance()).thenReturn(atmBalance);
		Mockito.when(atmMachineDao.validateWithdrawalAmount(withdrawalAmount)).thenReturn(Boolean.TRUE);
		Mockito.doThrow(NullPointerException.class).when(customerAccountDao).fetchCustomerBalance(inputAccountNumber,pin);
		
		try {
			atmMachineRequestManager.withdrawMoney(inputAccountNumber,pin, withdrawalAmount);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			Mockito.verify(atmMachineDao).fethcATMBalance();
			Mockito.verify(atmMachineDao).validateWithdrawalAmount(withdrawalAmount);
			Mockito.verify(customerAccountDao).fetchCustomerBalance(inputAccountNumber,pin);
			Mockito.verifyNoMoreInteractions(customerAccountDao);
			Mockito.verify(atmMachineDao, Mockito.times(0)).dispenseMoney(withdrawalAmount);
		}
	}
	
}



