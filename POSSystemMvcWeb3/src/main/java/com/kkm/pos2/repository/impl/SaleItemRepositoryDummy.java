package com.kkm.pos2.repository.impl;

import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.repository.SaleItemRepository;

public class SaleItemRepositoryDummy implements SaleItemRepository {

	@Override
	public SaleItem save(Item item, int quantity) {
		return new SaleItem(item, quantity);
	}

}
