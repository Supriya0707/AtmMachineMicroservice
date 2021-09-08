package com.atm.atmmachine.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.model.Denomination;

public class ApplicationData {

	private ATMMachine atmMachine;

	private static List<Customer> customerAll = null;
	
	private static TreeSet<Denomination> denominationAll = null;
	
	public ATMMachine getAtmMachine() {
		return atmMachine;
	}

	// ATM initialization
	@Autowired
	public void setAtmMachine(ATMMachine atmMachine) {
		
		atmMachine.setCustomersAll(ApplicationData.getCustomerAll());
		atmMachine.setDenominationAll(getDenominationAll());
		
		this.atmMachine = atmMachine;
	}

	/**
	 * Creating/Setting the customers locally.
	 * Not being set from the properties file to hide the PIN numbers.
	 */
	private static List<Customer> getCustomerAll() {
		
		if (customerAll == null) {
			customerAll = new ArrayList<Customer>();
			customerAll.add(new Customer("123456789", 1234, 800, 200));
			customerAll.add(new Customer("987654321", 4321, 1230, 150));
		}
		return customerAll;
	}
	
	/**
	 * TODO - to be removed
	 * Properties not being loaded as a List from properties file.
	 * So temporarily setting from here.
	 */
	private static TreeSet<Denomination> getDenominationAll() {
		
		if (denominationAll == null) {
			denominationAll = new TreeSet<Denomination>();
			denominationAll.add(new Denomination(50, 20));
			denominationAll.add(new Denomination(20, 30));
			denominationAll.add(new Denomination(10, 30));
		}
		return denominationAll;
	}

}
