package com.kkm.pos2.repository;

import java.util.List;

import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.exception.RepositoryException;

public interface ItemRepository {
	
	Item findByCode(String code)throws RepositoryException;
	void save();
	List<Item> findAll() throws RepositoryException;
	
	
}
