package com.atm.atmmachine.environment;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.atm.atmmachine.model.Customer;

@ConfigurationProperties(prefix="com.atm.atmmachine.resourcemappings")
public class ATMMachine {

	private List<Customer> customersAll;

	public List<Customer> getCustomersAll() {
		return customersAll;
	}

	public void setCustomersAll(List<Customer> customersAll) {
		this.customersAll = customersAll;
	}
	

}
