package com.atm.atmmachine.dao;

import java.util.List;

import com.atm.atmmachine.model.Denomination;
import com.atm.atmmachine.model.Withdrawal;

public interface ATMMachineDao {

	public long fethcATMBalance();

	public boolean validateWithdrawalAmount(int amount);
	
	public List<Denomination> dispenseMoney(int amount);
	
	public List<Withdrawal> fethcAllWithdrawals();
	
	public void updateWithdrawals(String accountNumber, int amount);
	
}
