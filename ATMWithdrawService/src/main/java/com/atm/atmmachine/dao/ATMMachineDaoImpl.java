package com.atm.atmmachine.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.model.Denomination;

public class ATMMachineDaoImpl implements ATMMachineDao {

	@Autowired
	private ApplicationData applicationData;
	
	@Override
	public long fethcATMBalance() {
		return applicationData.getBalanceMoney();
	}

	@Override
	public boolean validateWithdrawalAmount(int amount) {
		boolean isAmountValid = false;
		int lowestNoteValue = ApplicationData.getDenominationAll().last().getNoteValue();
		if (amount > 0 && ((amount % lowestNoteValue) == 0)) {
			return true;
		} 
		return isAmountValid;
	}
	
	@Override
	public synchronized List<Denomination> dispenseMoney(int amount) {
		
		List<Denomination> denominationResult = new ArrayList<>();
		return calculateWithAllDenomination(amount, denominationResult);
	}

	private List<Denomination> calculateWithAllDenomination(int amount, List<Denomination> denominationResult) {
		
		int remainingAmt = 0;
		// Get All available notes and its Count in ATM Machine
		TreeSet<Denomination> denominationAll = ApplicationData.getDenominationAll();
		
		Denomination updatedDenomination = null;	// here new quantity will be updated, after calculation
		
		Iterator<Denomination> iterator = denominationAll.iterator();
		while (iterator.hasNext()) {
			Denomination currentDenomination = iterator.next();
			updatedDenomination = currentDenomination;
			if (amount >= currentDenomination.getNoteValue() && currentDenomination.getNoteQuantity() > 0) {
				remainingAmt = calculateWithDenomination(amount, currentDenomination, updatedDenomination, denominationResult);
				break;
			}
		}
		
		// Update repository
		denominationAll.add(updatedDenomination);
		applicationData.setDenominationAll(denominationAll);
		
		// recursion for remainingAmt with updated Denomination
		if (remainingAmt > 0) {
			calculateWithAllDenomination(remainingAmt, denominationResult);	
		}
		
		return denominationResult;
	}
	
	private int calculateWithDenomination(int amount, Denomination currentDenomination, Denomination updatedDenomination, List<Denomination> denominationResult) {
		
		Denomination denominationToBeGiven = null;
		
		int remainingAmt = amount % currentDenomination.getNoteValue();
		int noteQuantityRequired = amount / currentDenomination.getNoteValue();
		int noteQuantityToBeGiven = 0;
		
		if (noteQuantityRequired > currentDenomination.getNoteQuantity()) {
			noteQuantityToBeGiven = currentDenomination.getNoteQuantity();
			remainingAmt = amount - (currentDenomination.getNoteQuantity() * currentDenomination.getNoteValue());
		} else {
			noteQuantityToBeGiven = noteQuantityRequired;
		}
		
		// Preparation for repository update
		updatedDenomination.setNoteQuantity(currentDenomination.getNoteQuantity() - noteQuantityToBeGiven);
		
		// Update Result
		denominationToBeGiven = new Denomination(currentDenomination.getNoteValue(), noteQuantityToBeGiven);
		denominationResult.add(denominationToBeGiven);
		
		return remainingAmt;
	}

}
