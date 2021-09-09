package com.atm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.manager.ATMMachineRequestManager;

@SpringBootTest
class AtmCustomerServiceApplicationTests {

	@InjectMocks
	private ATMMachineRequestManager atmMachineRequestManager;
	
	@Mock
	private CustomerAccountDao customerAccountDao;
	
	
	@Test
	public void test_fetchCustomerBalance_invalidAccountNumber() {
		
		String inputAccountNumber = "111";
		Mockito.doThrow(NullPointerException.class).when(customerAccountDao).fetchCustomerBalance(inputAccountNumber);
		
		try {
			atmMachineRequestManager.fetchCustomerBalance(inputAccountNumber);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}
	}

}
