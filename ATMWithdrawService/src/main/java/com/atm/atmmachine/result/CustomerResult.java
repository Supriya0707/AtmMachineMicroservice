package com.atm.atmmachine.result;

import java.util.List;

import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.model.Denomination;
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
	
	@JsonProperty("withdrawnNotes")
	private List<Denomination> denominationResult;

	public List<Denomination> getDenominationResult() {
		return denominationResult;
	}

	public void setDenominationResult(List<Denomination> denominationResult) {
		this.denominationResult = denominationResult;
	}

	public String getWithdrawnAmount() {
		return withdrawnAmount;
	}

	public void setWithdrawnAmount(String withdrawnAmount) {
		this.withdrawnAmount = withdrawnAmount;
	}
	
}
