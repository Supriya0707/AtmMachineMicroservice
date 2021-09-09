package com.atm.atmmachine.environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.atm.atmmachine.model.Customer;
import com.atm.atmmachine.model.Denomination;
import com.atm.atmmachine.model.Withdrawal;

public class ApplicationData {

	private static List<Customer> customerAll = null;

	private static TreeSet<Denomination> denominationAll = null;

	private List<Withdrawal> withdrawalsAll;

	/**
	 * Creating/Setting the customers locally.
	 */
	public static List<Customer> getCustomerAll() {

		if (customerAll == null) {
			customerAll = new ArrayList<Customer>();
			customerAll.add(new Customer("123456789", 1234, 800));
			customerAll.add(new Customer("987654321", 4321, 1230));
		}
		return customerAll;
	}

	/**
	 * Created Denomination List Locally
	 */
	public static TreeSet<Denomination> getDenominationAll() {

		if (denominationAll == null) {
			denominationAll = new TreeSet<Denomination>();
			denominationAll.add(new Denomination(50, 20));
			denominationAll.add(new Denomination(20, 30));
			denominationAll.add(new Denomination(10, 30));
		}
		return denominationAll;
	}

	public void setDenominationAll(TreeSet<Denomination> denominationAll) {
		this.denominationAll = denominationAll;
	}

	public void setCustomersAll(List<Customer> customersAll) {
		this.customerAll = customersAll;
	}

	public List<Withdrawal> getWithdrawalsAll() {
		return withdrawalsAll;
	}

	public void setWithdrawalsAll(List<Withdrawal> withdrawalsAll) {
		this.withdrawalsAll = withdrawalsAll;
	}
	

	public long getBalanceMoney() {

		long balanceMoney = 0;
		Iterator<Denomination> iterator = getDenominationAll().iterator();

		while (iterator.hasNext()) {
			Denomination d = iterator.next();
			balanceMoney += (d.getNoteValue() * d.getNoteQuantity());
		}

		return balanceMoney;
	}
}
