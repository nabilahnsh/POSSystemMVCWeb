package com.kkm.pos2.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.repository.CashierRepository;

public class CashierRepositoryDummy implements CashierRepository{

	@Override
	public void save(Cashier cashier) throws RepositoryException {
		System.out.println("Successfully add Cashier data!");
		
	}

	@Override
	public Cashier findCashierById(String cashierID) throws RepositoryException {
		Cashier cashier1 = new Cashier("01", "Wafda");
		Cashier cashier2 = new Cashier("02", "Alisa");
		
		List<Cashier> cashier = new ArrayList<Cashier>(Arrays.asList(cashier1, cashier2));
		
		for(Cashier c : cashier) {
			if(c.getCashierID().equals(cashierID)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public List<Cashier> findAll() throws RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

}
