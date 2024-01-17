package com.kkm.pos2.repository;

import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.SaleItem;

public interface SaleItemRepository {
	SaleItem save(Item item, int quantity);
	
}
