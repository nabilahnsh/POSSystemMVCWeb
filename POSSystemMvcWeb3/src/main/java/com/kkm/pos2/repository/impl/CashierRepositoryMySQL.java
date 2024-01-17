package com.kkm.pos2.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kkm.pos2.database.DatabaseConnection;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.exception.DatabaseConnectionException;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.repository.CashierRepository;

public class CashierRepositoryMySQL implements CashierRepository {

	@Override
	public void save(Cashier cashier) throws RepositoryException {
		try (Connection conn = DatabaseConnection.conn()){
		//insert cashier
		String sql3 = "INSERT INTO cashier(cashier_id, cashier_name) values (?,?)";
		PreparedStatement stm3 = conn.prepareStatement(sql3);	
		
		stm3.setString(1, cashier.getCashierID());
		stm3.setString(2, cashier.getCashierName());
		stm3.executeUpdate();
		
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} 
	}

	@Override
	public Cashier findCashierById(String cashierID) throws RepositoryException {
		Cashier result = null;
		
		try (Connection conn = DatabaseConnection.conn()){
			String sqlQuery = "SELECT * FROM cashier WHERE cashier_id = ?";
			
			PreparedStatement stm;
			stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, cashierID);
			
			ResultSet rs = stm.executeQuery();
				if(rs.next()) {
					result = new Cashier(rs.getString("cashier_id"), rs.getString("cashier_name"));
				}
			
				
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} return result;
	}

	@Override
	public List<Cashier> findAll() throws RepositoryException {
		List<Cashier> cashier = new ArrayList<Cashier>();
		try (Connection conn = DatabaseConnection.conn()){
			String sqlQuery = "SELECT * FROM cashier";
		
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sqlQuery);
		
		while(rs.next()) {
			cashier.add(new Cashier(rs.getString("cashierID"), rs.getString("cashierName")));
		}
	} catch (SQLException e) {
		throw new RepositoryException("Cannot get data from database");
	} 
		return cashier;
	}
	
	public Cashier cashierLogin(String id, String pass) {
		Cashier c = null;
		try (Connection conn = DatabaseConnection.conn()) {
			//Class.forName("com.mysql.jdbc.Driver");
			String sqlQuery = "SELECT * FROM cashier WHERE cashier_id = ? and password = ?";
			
			PreparedStatement stm;
			stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, id);
			stm.setString(2, pass);
			
			ResultSet rs = stm.executeQuery();
				if(rs.next()) {
					c = new Cashier(rs.getString("cashier_id"), rs.getString("cashier_name"), rs.getString("password"));
				}
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		} 
//		catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return c;
	}
}
