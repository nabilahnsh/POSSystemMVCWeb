package com.kkm.pos2.repository.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.repository.SaleRepository;

public class SaleRepositoryDummy implements SaleRepository {
	public static List<Sale> sales = new ArrayList<Sale>();

	@Override
	public void save(Sale sale) {
		sales.add(sale);

	}

	public Sale findByNumber(String number) {
		Iterator<Sale> it = sales.iterator();
		
		while(it.hasNext()) {
			Sale s = it.next();
			if(s.getSaleNumber() == number) {
				return s;
			}
		} return null;
		
	}
}
