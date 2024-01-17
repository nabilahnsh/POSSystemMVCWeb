package com.kkm.pos2.repository;

import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.exception.RepositoryException;

public interface SaleRepository {
	
	void save(Sale sale)throws RepositoryException;
	Sale findByNumber(String number) throws RepositoryException;
}
