package com.kkm.pos2.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import com.kkm.pos2.repository.impl.ItemRepositoryMySQL;
import com.kkm.pos2.repository.impl.SaleRepositoryMySQL;

public class TestDatabase {
	static SaleRepositoryMySQL repoSale = new SaleRepositoryMySQL();
	static ItemRepositoryMySQL itemRepo = new ItemRepositoryMySQL();

	public static void main(String[] args) throws RepositoryException {

		List<Sale> listSale = new ArrayList<Sale>();

		Sale result = null;
		String number = "20240115090701";
		try (Connection conn = DatabaseConnection.conn()) {
			String sqlQuery = "SELECT sale.sale_number, sale.cashier_id, cashier.cashier_name, payment.amount, payment.payment, payment.tax "
					+ "FROM sale " + "INNER JOIN cashier ON cashier.cashier_id = sale.cashier_id "
					+ "INNER JOIN payment ON payment.sale_number = sale.sale_number " + "WHERE sale.sale_number = ?";

			PreparedStatement stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, number);

			ResultSet rs = stm.executeQuery();

			while (rs.next()) {
				String saleNumber = rs.getString("sale_number");
				String cashierID = rs.getString("cashier_id");
				String cashierName = rs.getString("cashier_name");
				double amount = rs.getDouble("amount");
				String payment = rs.getString("payment");
				double tax = rs.getDouble("tax");

				Cashier cashier = new Cashier(cashierID, cashierName);
				Payment p = null;

				result = new Sale(rs.getString("sale_number"), cashier);

				if ("cash".equals(payment)) {
					String sqlQueryP = "SELECT sale.sale_number, cash_payment.cash_in_hand " + "FROM sale "
							+ "INNER JOIN cash_payment ON sale.sale_number = cash_payment.payment "
							+ "WHERE sale.sale_number = ?";

					PreparedStatement stm3 = conn.prepareStatement(sqlQueryP);
					stm3.setString(1, result.getSaleNumber());

					ResultSet rs3 = stm3.executeQuery();

					while (rs3.next()) {
						double cashInHand = rs3.getDouble("cash_in_hand");
						p = new CashPayment(cashInHand);
					}
				} else {
					p = new QrisPayment();
				}
				result.setPayment(p);
				result.setSaleTax(tax);

				// panggil saleitem
				String sqlQuerySI = "SELECT sale.sale_number,sale_item.item_code,item.price,item.description,item.type,item.taxable,sale_item.quantity "
						+ "FROM sale_item " + "INNER JOIN item ON item.item_code = sale_item.item_code "
						+ "INNER JOIN sale ON sale_item.sale_number = sale.sale_number " + "WHERE sale.sale_number = ?";

				PreparedStatement stm2 = conn.prepareStatement(sqlQuerySI);
				stm2.setString(1, result.getSaleNumber());

				ResultSet rs2 = stm2.executeQuery();

				while (rs2.next()) {
					boolean taxable;
					if (rs2.getInt("taxable") == 0) {
						taxable = true;
					} else {
						taxable = false;
					}

					Item item = new Item(rs2.getString("item_code"), rs2.getInt("price"), rs2.getString("description"),
							rs2.getString("type"), taxable);
					SaleItem saleItem = new SaleItem(item, rs2.getInt("quantity"));
					result.addSaleItem(saleItem);
				}

				// listSale.add(sale);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(result.getSaleNumber());
		System.out.println(result.getCashier().getCashierName());
		for(SaleItem si : result.getSaleItems()) {
			System.out.println(si.getItem().getItemCode());
		}
		System.out.println(result.getPayment());
		
	}

}
