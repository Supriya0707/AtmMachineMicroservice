package com.atm.atmmachine.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.result.ATMMachineRequestResult;


//@FeignClient(name="enquiry-service", url="localhost:8000")
@FeignClient(name="enquiry-service")
public interface EnquiryServiceProxy {
	
	@GetMapping("/balanceEnquiry?accountNumber={accountNo}&pin={pin}")
	public ATMMachineRequestResult getBalanceEnquiry(
			@PathVariable String accountNo,
			@PathVariable int pin);
	
	@GetMapping("/findCustomer?accountNumber={accountNo}")
	public Customer findCustomer(
			@PathVariable String accountNo);

}
