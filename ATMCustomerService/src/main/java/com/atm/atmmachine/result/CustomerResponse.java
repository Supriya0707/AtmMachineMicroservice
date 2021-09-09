package com.atm.atmmachine.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CustomerResponse {

	// @JsonProperty("error")
	private ServiceError error;
	
	@JsonProperty("serviceResponse")
	private CustomerResult customerResult;
	

	public CustomerResult getCustomerResult() {
		return customerResult;
	}

	public void setCustomerResult(CustomerResult customerResult) {
		this.customerResult = customerResult;
	}

	public ServiceError getServiceError() {
		return error;
	}

	public void setServiceError(ServiceError error) {
		this.error = error;
	}

}
