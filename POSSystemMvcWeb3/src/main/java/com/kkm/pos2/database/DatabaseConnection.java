package com.kkm.pos2.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.kkm.pos2.exception.DatabaseConnectionException;

public class DatabaseConnection {
	private Connection conn;
	public static final String userName = "root";
	public static final String password = "";
	public static final String jdbcUrl = "jdbc:mysql://localhost:3306/pos2_db";

	public DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection(jdbcUrl, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Connection getConn() {
		return conn;
	}

	public static Connection conn() {
		return new DatabaseConnection().getConn();
	}

	
	
	
}
