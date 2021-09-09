package com.atm.atmmachine.dao;

import java.util.List;

import com.atm.atmmachine.model.Denomination;

public interface ATMMachineDao {

	public long fethcATMBalance();

	public boolean validateWithdrawalAmount(int amount);
	
	public List<Denomination> dispenseMoney(int amount);
		
}
