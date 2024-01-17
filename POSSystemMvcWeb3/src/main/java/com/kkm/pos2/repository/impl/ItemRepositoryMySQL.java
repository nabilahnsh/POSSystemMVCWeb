package com.kkm.pos2.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kkm.pos2.database.DatabaseConnection;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.exception.DatabaseConnectionException;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.repository.ItemRepository;

public class ItemRepositoryMySQL implements ItemRepository{
	
		
	@Override
	public Item findByCode(String code) throws RepositoryException {
		Item result = null;
		
		try (Connection conn = DatabaseConnection.conn()){

			String sqlQuery = "SELECT * FROM item WHERE item_code = ?";
			
			PreparedStatement stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, code);
			
			ResultSet rs = stm.executeQuery();
				if(rs.next()) {
					result = new Item(rs.getString("item_code"), rs.getDouble("price"), rs.getString("description"), rs.getString("type"), rs.getBoolean("taxable"));
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RepositoryException("Cannot get data from database");
		}	
		return result;
	}
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Item> findAll() throws RepositoryException {
		List<Item> listItem = new ArrayList<Item>();
		try (Connection conn = DatabaseConnection.conn()){

			//Class.forName("com.mysql.jdbc.Driver");

			String sqlQuery = "SELECT * FROM item ORDER BY `item_code` ASC";
			
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sqlQuery);
			
			//add item
			while(rs.next()) {
				boolean isTaxable;
				if(rs.getInt("taxable") == 1) {
					isTaxable = true;
				} else {
					isTaxable = false;
				}
				listItem.add(new Item(rs.getString("item_code"), rs.getDouble("price"), rs.getString("description"), rs.getString("type"), isTaxable));
			}
		} catch (SQLException e) {
			throw new RepositoryException("Cannot get data from database");
		}   return listItem;
	}
	
	public void editItem(Item item) throws RepositoryException{
		try (Connection conn = DatabaseConnection.conn()){
			//Class.forName("com.mysql.jdbc.Driver");
			String sql = "UPDATE item SET price=?, description=?, type=?, taxable=? WHERE item_code=?";
	        PreparedStatement stm = conn.prepareStatement(sql);
	        
	            stm.setDouble(1, item.getItemPrice());
	            stm.setString(2, item.getDescription());
	            stm.setString(3, item.getType());
	            stm.setBoolean(4, item.isTaxable());
	            stm.setString(5, item.getItemCode());

	            stm.executeUpdate();
	            
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} 
		
	}
	
	public void addItem(Item item) throws RepositoryException{

		try (Connection conn = DatabaseConnection.conn()){
			//Class.forName("com.mysql.jdbc.Driver");
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO item(item_code, price, description, type, taxable) values (?,?,?,?,?)";
			PreparedStatement stm = conn.prepareStatement(sql);
			
			stm.setString(1, item.getItemCode());
			stm.setDouble(2, item.getItemPrice());
			stm.setString(3, item.getDescription());
			stm.setString(4, item.getType());
			stm.setBoolean(5, item.isTaxable());
			
			stm.executeUpdate();
			
			conn.commit();
			
			
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} 
		
}
	
	public void deleteItem(String code) throws RepositoryException{
		try (Connection conn = DatabaseConnection.conn()){
			//Class.forName("com.mysql.jdbc.Driver");
			String sql = "DELETE FROM item WHERE item_code = ?";
	        PreparedStatement stm = conn.prepareStatement(sql);
	        
	        stm.setString(1, code);

	        stm.executeUpdate();
	            
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} 
		
	}

}
