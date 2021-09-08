package com.atm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.atm.atmmachine.constants.ATMMachineErrorKeys;
import com.atm.atmmachine.dao.ATMMachineDao;
import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.exception.ATMMachineException;
import com.atm.atmmachine.exception.UserAccountException;
import com.atm.atmmachine.manager.ATMMachineRequestManager;
import com.atm.atmmachine.model.Denomination;
import com.atm.atmmachine.result.ATMMachineRequestResult;
import com.atm.atmmachine.result.CustomerResult;

@SpringBootTest
class AtmWithdrawServiceApplicationTests {

    
	@InjectMocks
	private ATMMachineRequestManager atmMachineRequestManager;
	
	@Mock
	private CustomerAccountDao customerAccountDao;
	
	@Mock
	private ATMMachineDao atmMachineDao;
	
	
	@Test
	public void test_fetchCustomerBalance_invalidAccountNumber() {
		
		String inputAccountNumber = "111";
		int pin = 1234;
		Mockito.doThrow(NullPointerException.class).when(customerAccountDao).fetchCustomerBalance(inputAccountNumber,pin);
		
		try {
			atmMachineRequestManager.fetchCustomerBalance(inputAccountNumber,pin);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}
	}
	
	@Test
	public void test_withdrawMoney_validInputsCustomerHasSufficientBalance() {
		
		long atmBalance = 1000;
		String inputAccountNumber = "111111111";
		int pin = 1234;
		int withdrawalAmount = 400;
		long customerBalance = 900;
		long customerBalanceNew = 500;
		int customerOverdraft = 100;
		int customerOverdraftNew = 100;
		List<Denomination> denominationResult = TestUtility.getDenominationResult();
		
		Mockito.when(atmMachineDao.fethcATMBalance()).thenReturn(atmBalance);
		Mockito.when(atmMachineDao.validateWithdrawalAmount(withdrawalAmount)).thenReturn(Boolean.TRUE);
		Mockito.when(customerAccountDao.fetchCustomerBalance(inputAccountNumber,pin)).thenReturn(customerBalance);
		Mockito.when(customerAccountDao.fetchCustomerOverdraft(inputAccountNumber)).thenReturn(customerOverdraft);
		Mockito.when(atmMachineDao.dispenseMoney(withdrawalAmount)).thenReturn(denominationResult);
		Mockito.doNothing().when(customerAccountDao).updateCustomerBalance(inputAccountNumber, customerBalanceNew);
		Mockito.doNothing().when(customerAccountDao).updateCustomerOverdraft(inputAccountNumber, customerOverdraftNew);
		
		ATMMachineRequestResult requestResult = atmMachineRequestManager.withdrawMoney(inputAccountNumber,pin, withdrawalAmount);
		CustomerResult customer = requestResult.getCustomerResult();
		
		Mockito.verify(customerAccountDao).fetchCustomerBalance(inputAccountNumber,pin);
		Mockito.verify(customerAccountDao, Mockito.times(0)).fetchCustomerOverdraft(inputAccountNumber);
		Mockito.verify(customerAccountDao).updateCustomerBalance(inputAccountNumber, customerBalanceNew);
		Mockito.verify(customerAccountDao, Mockito.times(0)).updateCustomerOverdraft(inputAccountNumber, customerOverdraftNew);
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



