package com.atm.atmmachine.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.result.WithdrawResponse;


//@FeignClient(name="customer-service", url="localhost:8000")
@FeignClient(name="customer-service")
public interface CustomerServiceProxy {
	
	@GetMapping("/balanceEnquiry?accountNumber={accountNo}&pin={pin}")
	public WithdrawResponse getBalanceEnquiry(
			@PathVariable String accountNo,
			@PathVariable int pin);
	
	@GetMapping("/findCustomer?accountNumber={accountNo}")
	public Customer findCustomer(
			@PathVariable String accountNo);
	
	
	@PostMapping("/updateCustomerBalance?accountNumber={accountNo}&pin={pin}&amount={amount}")
	public WithdrawResponse updateCustomerBalance(
			@PathVariable String accountNo,
			@PathVariable int pin, @PathVariable long amount);

}
