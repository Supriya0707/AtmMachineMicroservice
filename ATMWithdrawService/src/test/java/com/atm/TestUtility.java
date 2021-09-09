package com.atm;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.model.Denomination;

public class TestUtility {

	public static List<Denomination> getDenominationResult() {
		
		List<Denomination> denominationResult = new ArrayList<>();
		denominationResult.add(new Denomination(50, 1));
		denominationResult.add(new Denomination(20, 1));
		denominationResult.add(new Denomination(10, 1));
		denominationResult.add(new Denomination(5, 1));
		
		return denominationResult;
	}
	
	public static ApplicationData getApplicationData() {
		
		ApplicationData appData = new ApplicationData();
		appData.setCustomersAll(getCustomerAll());
		appData.setDenominationAll(getDenominationAll());
		return appData;
	}
	

	public static List<Customer> getCustomerAll() {
		
		List<Customer> customerAll = new ArrayList<Customer>();
		customerAll.add(new Customer("111111111", 1234, 100));
		customerAll.add(new Customer("222222222", 4321, 200));
		return customerAll;
	}
	
	public static TreeSet<Denomination> getDenominationAll() {
		
		TreeSet<Denomination> denominationAll = new TreeSet<Denomination>();
		denominationAll.add(new Denomination(50, 1));
		denominationAll.add(new Denomination(20, 1));
		denominationAll.add(new Denomination(10, 1));
		denominationAll.add(new Denomination(5, 1));

		return denominationAll;
	}
	
}
