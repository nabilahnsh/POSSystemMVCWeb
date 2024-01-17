package com.kkm.pos2.repository.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kkm.pos2.domain.Item;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.repository.ItemRepository;

public class ItemRepositoryFile implements ItemRepository{

	private List<Item> listItem = new ArrayList<Item>();
	
	
	@Override
	public Item findByCode(String code) {
				
		Iterator<Item> it = listItem.iterator();
		while(it.hasNext()) {
			Item item = it.next();
			if (item.getItemCode().equals(code)) {
				return item;
			}
		
	}return null;

}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Item> findAll() throws RepositoryException {
		
		FileReader fileReader;
		try {
			fileReader = new FileReader("catalogItem.txt");
			BufferedReader bufReader = new BufferedReader(fileReader);
			
			String line = null;
			while((line = bufReader.readLine()) != null) {
				String[] tokens = line.split(";");
				
				boolean isTaxable;
				if(Integer.parseInt(tokens[4]) == 1) {
					isTaxable =  true;
				} else {
					isTaxable = false;
				}

				listItem.add(new Item(tokens[0], Double.parseDouble(tokens[1]), tokens[2], tokens[3], isTaxable));
		} 
			
		} catch (IOException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} return listItem;
	}
}
