package com.kkm.pos2.repository;

import java.util.List;

import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.exception.RepositoryException;

public interface CashierRepository {
	void save(Cashier cashier) throws RepositoryException;
	Cashier findCashierById(String cashierID) throws RepositoryException;
	List<Cashier> findAll() throws RepositoryException;
}
