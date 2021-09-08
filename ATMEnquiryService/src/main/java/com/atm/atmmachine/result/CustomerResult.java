package com.atm.atmmachine.result;

import com.atm.atmmachine.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CustomerResult extends Customer {

	// TODO Change to integer and Check jackson rule to exclude this if zero
	@JsonProperty("withdrawnAmount")
	private String withdrawnAmount;
	
	public String getWithdrawnAmount() {
		return withdrawnAmount;
	}

	public void setWithdrawnAmount(String withdrawnAmount) {
		this.withdrawnAmount = withdrawnAmount;
	}
	
}
