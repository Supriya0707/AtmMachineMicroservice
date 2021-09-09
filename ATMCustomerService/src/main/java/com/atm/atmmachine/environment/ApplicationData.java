package com.atm.atmmachine.environment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.model.Customer;

public class ApplicationData {

	private static List<Customer> customerAll = null;

	/**
	 * Creating/Setting the customers locally.
	 * Not being set from the properties file to hide the PIN numbers.
	 */
	public static List<Customer> getCustomerAll() {
		
		if (customerAll == null) {
			customerAll = new ArrayList<Customer>();
			customerAll.add(new Customer("123456789", 1234, 800));
			customerAll.add(new Customer("987654321", 4321, 1230));
		}
		return customerAll;
	}
	
	public void setCustomersAll(List<Customer> customersAll) {
		this.customerAll = customersAll;
	}
	
}
