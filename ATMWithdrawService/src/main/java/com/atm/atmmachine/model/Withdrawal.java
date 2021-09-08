package com.atm.atmmachine.model;

import java.util.Date;

public class Withdrawal {

	private int withdrawnAmount;
	
	private Date withdrawnDateTime;

	public Withdrawal(int withdrawnAmount, Date withdrawnDateTime) {
		super();
		this.withdrawnAmount = withdrawnAmount;
		this.withdrawnDateTime = withdrawnDateTime;
	}

	public int getWithdrawnAmount() {
		return withdrawnAmount;
	}

	public void setWithdrawnAmount(int withdrawnAmount) {
		this.withdrawnAmount = withdrawnAmount;
	}

	public Date getWithdrawnDateTime() {
		return withdrawnDateTime;
	}

	public void setWithdrawnDateTime(Date withdrawnDateTime) {
		this.withdrawnDateTime = withdrawnDateTime;
	}
	
}
