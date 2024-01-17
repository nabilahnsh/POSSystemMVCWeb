package com.kkm.pos2.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kkm.pos2.database.DatabaseConnection;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.repository.SaleItemRepository;

public class SaleItemRepositoryMySQL implements SaleItemRepository {
	ItemRepositoryMySQL itemRepo = new ItemRepositoryMySQL();
	
	@Override
	public SaleItem save(Item item, int quantity) {
		return new SaleItem(item, quantity);
	}
	
	public List<SaleItem> findAll(){
		List<SaleItem> listSaleItem = new ArrayList<SaleItem>();
		try (Connection conn = DatabaseConnection.conn()){

			String sqlQuery = "SELECT * FROM sale_item";
			
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sqlQuery);
			
			while(rs.next()) {
				
				listSaleItem.add(new SaleItem(itemRepo.findByCode(rs.getString("item_code")),rs.getInt("quantity")));
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} return listSaleItem;
		
	}
	
	public SaleItem findByNumber(String number) throws RepositoryException {
		SaleItem result = null;
		
		try (Connection conn = DatabaseConnection.conn()){
			String sqlQuery = "SELECT sale.sale_number, sale_item.item_code, sale_item.quantity FROM sale_item INNER JOIN sale ON sale_item.sale_number = sale.sale_number WHERE sale.sale_number = ?";
			
			PreparedStatement stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, number);
			
			ResultSet rs = stm.executeQuery();
				if(rs.next()) {
					result = new SaleItem(itemRepo.findByCode(rs.getString("item_code")),rs.getInt("quantity"));
				}
		
		}catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
	}

}
