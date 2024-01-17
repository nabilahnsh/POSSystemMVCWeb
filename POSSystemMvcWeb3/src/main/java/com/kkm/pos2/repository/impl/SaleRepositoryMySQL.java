package com.kkm.pos2.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kkm.pos2.database.DatabaseConnection;
import com.kkm.pos2.domain.CashPayment;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Payment;
import com.kkm.pos2.domain.QrisPayment;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.exception.DatabaseConnectionException;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.repository.CashierRepository;
import com.kkm.pos2.repository.ItemRepository;
import com.kkm.pos2.repository.SaleRepository;

public class SaleRepositoryMySQL implements SaleRepository{
	public static List<Sale> sales = new ArrayList<Sale>();
	protected ItemRepository itemRepository;
	protected CashierRepository cashierRepository;
	
	@Override
	public void save(Sale sale) throws RepositoryException {
		//sales.add(sale);
		
		try (Connection conn = DatabaseConnection.conn()){
			conn.setAutoCommit(false);
			
			//insert sale
			String sql = "INSERT INTO sale(sale_number, trans_date, cashier_id) values (?,?,?)";
			PreparedStatement stm = conn.prepareStatement(sql);
			
			stm.setString(1, sale.getSaleNumber());
			stm.setDate(2, new Date (sale.getTransDate().getTime()));
			stm.setString(3, sale.getCashier().getCashierID());

			stm.executeUpdate();
			
			//insert sale_item
			String sql2 = "INSERT INTO sale_item(sale_number, item_code, quantity) values (?,?,?)";
			for(SaleItem s: sale.getSaleItems()) {
				
				PreparedStatement stm2 = conn.prepareStatement(sql2);
				
				stm2.setString(1, sale.getSaleNumber());
				stm2.setString(2, s.getItem().getItemCode());
				stm2.setInt(3, s.getQuantity());
				stm2.executeUpdate();
			}
			
			//insert payment
			String sql4 = "INSERT INTO payment(amount, isPay, sale_number, payment, tax) values (?,?,?,?,?)";
			PreparedStatement stm4 = conn.prepareStatement(sql4);
			
			stm4.setDouble(1, sale.grandTotal()+sale.totalTaxPayment());
			stm4.setInt(2, 1);
			stm4.setString(3, sale.getSaleNumber());
			if(sale.getPayment() instanceof CashPayment) {
				stm4.setString(4, "cash");
			}else {
				stm4.setString(4, "qris");
			}
			stm4.setDouble(5, sale.totalTaxPayment());
			
			stm4.executeUpdate();
			
			if(sale.getPayment() instanceof CashPayment) {
				String sqlCash = "INSERT INTO cash_payment(cash_in_hand , payment) values (?,?)";
				PreparedStatement stmCash = conn.prepareStatement(sqlCash);
				stmCash.setDouble(1, ((CashPayment) sale.getPayment()).getCashInHand());
				stmCash.setString(2, sale.getSaleNumber());
				stmCash.executeUpdate();
			} else {
				String sqlQris = "INSERT INTO qris_payment(amount , payment) values (?,?)";
				PreparedStatement stmQris = conn.prepareStatement(sqlQris);
				stmQris.setDouble(1, sale.grandTotal()+sale.totalTaxPayment());
				stmQris.setString(2, sale.getSaleNumber());
				stmQris.executeUpdate();
			}
			
			conn.commit();
			
		} 	catch (SQLException e) {
			throw new RepositoryException(e.getMessage());	
				} 
			}
		
		
		
	

	@Override
	public Sale findByNumber(String number) throws RepositoryException {
		Sale result = null;
		
		try (Connection conn = DatabaseConnection.conn()){
			String sqlQuery = "SELECT * FROM sale WHERE sale_number = ?";
			
			PreparedStatement stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, number);
			
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				result = new Sale(rs.getString("sale_number"), findCashierById(rs.getString("cashier_id")));
		}
		
		}catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} 
				
		
		
		return result;
	}
	
	
	public Cashier findCashierById(String cashier) throws RepositoryException{
		Cashier result = null;
		
		try (Connection conn = DatabaseConnection.conn()) {
			String sqlQuery = "SELECT * FROM cashier WHERE cashier_id = ?";
			
			PreparedStatement stm;
			stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, cashier);
			
			ResultSet rs = stm.executeQuery();
				if(rs.next()) {
					result = new Cashier(rs.getString("cashier_id"), rs.getString("cashier_name"));
				}
			
				
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} return result;	
		
	} 
	
	public Sale findAllWebByNumber(String number) {
		Sale result = null;
		
		try (Connection conn = DatabaseConnection.conn()){
			String sqlQuery =  "SELECT sale.sale_number, sale.cashier_id, cashier.cashier_name, payment.amount, payment.payment, payment.tax "
					+ "FROM sale "
					+ "INNER JOIN cashier ON cashier.cashier_id = sale.cashier_id "
					+ "INNER JOIN payment ON payment.sale_number = sale.sale_number "
					+ "WHERE sale.sale_number = ?";
			
			PreparedStatement stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, number);

			ResultSet rs = stm.executeQuery();
			
			
			while(rs.next()) {
				String saleNumber = rs.getString("sale_number");
				String cashierID = rs.getString("cashier_id");
				String cashierName = rs.getString("cashier_name");
				double amount = rs.getDouble("amount");
				String payment = rs.getString("payment");
				double tax = rs.getDouble("tax");
				
				
				Cashier cashier = new Cashier(cashierID, cashierName);
				Payment p = null;
				
				
				
				result = new Sale(rs.getString("sale_number"),cashier);
				
				if("cash".equals(payment)) {
					String sqlQueryP = "SELECT sale.sale_number, cash_payment.cash_in_hand "
			                + "FROM sale "
			                + "INNER JOIN cash_payment ON sale.sale_number = cash_payment.payment "
			                + "WHERE sale.sale_number = ?";
					
					PreparedStatement stm3 = conn.prepareStatement(sqlQueryP);
					stm3.setString(1, result.getSaleNumber());
					
					ResultSet rs3 = stm3.executeQuery();
					
					while(rs3.next()) {
					double cashInHand = rs3.getDouble("cash_in_hand");
					p = new CashPayment(cashInHand );
					}
				}
				else {
					p = new QrisPayment();
				}
				result.setPayment(p);
				result.setSaleTax(tax);
				
				
				//panggil saleitem
				String sqlQuerySI = "SELECT sale.sale_number,sale_item.item_code,item.price,item.description,item.type,item.taxable,sale_item.quantity "
						+ "FROM sale_item "
						+ "INNER JOIN item ON item.item_code = sale_item.item_code "
						+ "INNER JOIN sale ON sale_item.sale_number = sale.sale_number "
						+ "WHERE sale.sale_number = ?";
				
				PreparedStatement stm2 = conn.prepareStatement(sqlQuerySI);
				stm2.setString(1, result.getSaleNumber());
				
				ResultSet rs2 = stm2.executeQuery();
				
				while(rs2.next()) {
					boolean taxable;
					if(rs2.getInt("taxable") == 0) {
						taxable = true;
					}
					else {
						taxable = false;
					}
					
					Item item = new Item(rs2.getString("item_code"), rs2.getInt("price"), rs2.getString("description"), rs2.getString("type"), taxable);
					SaleItem saleItem = new SaleItem(item,rs2.getInt("quantity"));
					result.addSaleItem(saleItem);
				}
				
				//listSale.add(sale);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		}
	
	public List<Sale> findAllWeb() {
		List<Sale> listSale = new ArrayList<Sale>();
		
		try (Connection conn = DatabaseConnection.conn()){
			String sqlQuery =  "SELECT sale.sale_number, sale.cashier_id, cashier.cashier_name, payment.amount, payment.payment, payment.tax "
					+ "FROM sale "
					+ "INNER JOIN cashier ON cashier.cashier_id = sale.cashier_id "
					+ "INNER JOIN payment ON payment.sale_number = sale.sale_number "
					+ "ORDER BY sale.sale_number DESC";
			
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sqlQuery);
			System.out.println(sqlQuery);
			while(rs.next()) {
				String saleNumber = rs.getString("sale_number");
				String cashierID = rs.getString("cashier_id");
				String cashierName = rs.getString("cashier_name");
				double amount = rs.getDouble("amount");
				String payment = rs.getString("payment");
				double tax = rs.getDouble("tax");
				
				
				Cashier cashier = new Cashier(cashierID, cashierName);
				Payment p = null;
				
				
				
				Sale sale = new Sale(rs.getString("sale_number"),cashier);
				
				if("cash".equals(payment)) {
					String sqlQueryP = "SELECT sale.sale_number, cash_payment.cash_in_hand "
			                + "FROM sale "
			                + "INNER JOIN cash_payment ON sale.sale_number = cash_payment.payment "
			                + "WHERE sale.sale_number = ?";
					
					PreparedStatement stm3 = conn.prepareStatement(sqlQueryP);
					stm3.setString(1, sale.getSaleNumber());
					
					ResultSet rs3 = stm3.executeQuery();
					
					while(rs3.next()) {
					double cashInHand = rs3.getDouble("cash_in_hand");
					p = new CashPayment(cashInHand );
					}
				}
				else {
					p = new QrisPayment();
				}
				sale.setPayment(p);
				sale.setSaleTax(tax);
				
				
				//panggil saleitem
				String sqlQuerySI = "SELECT sale.sale_number,sale_item.item_code,item.price,item.description,item.type,item.taxable,sale_item.quantity "
						+ "FROM sale_item "
						+ "INNER JOIN item ON item.item_code = sale_item.item_code "
						+ "INNER JOIN sale ON sale_item.sale_number = sale.sale_number "
						+ "WHERE sale.sale_number = ?";
				
				PreparedStatement stm2 = conn.prepareStatement(sqlQuerySI);
				stm2.setString(1, sale.getSaleNumber());
				
				ResultSet rs2 = stm2.executeQuery();
				
				while(rs2.next()) {
					boolean taxable;
					if(rs2.getInt("taxable") == 0) {
						taxable = true;
					}
					else {
						taxable = false;
					}
					
					Item item = new Item(rs2.getString("item_code"), rs2.getInt("price"), rs2.getString("description"), rs2.getString("type"), taxable);
					SaleItem saleItem = new SaleItem(item,rs2.getInt("quantity"));
					sale.addSaleItem(saleItem);
				}
				
				listSale.add(sale);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listSale;
		}
		
	public List<Sale> findAll() throws RepositoryException {
		List<Sale> listSale = new ArrayList<Sale>();
		try (Connection conn = DatabaseConnection.conn()){

			String sqlQuery = "SELECT sale.sale_number, sale.cashier_id, cashier.cashier_name FROM sale INNER JOIN cashier ON cashier.cashier_id = sale.cashier_id ORDER BY `sale_number` DESC";;
			
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sqlQuery);
			
			while(rs.next()) {
				Cashier cashier = new Cashier(rs.getString("cashier_id"), rs.getString("cashier_name"));
				listSale.add(new Sale(rs.getString("sale_number"),cashier));
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} return listSale;
		} 
	

				
	}

		


	


