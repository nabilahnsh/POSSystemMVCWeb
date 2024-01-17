package com.kkm.pos2.domain;

public class Cashier {

	private String cashierID;
	private String cashierName;
	private String password;
	
	public Cashier(String cashierID, String cashierName) {
		this.cashierID = cashierID;
		this.cashierName = cashierName;
	}

	public Cashier(String cashierID, String cashierName, String password) {
		this.cashierID = cashierID;
		this.cashierName = cashierName;
		this.password = password;
	}

	public String getCashierID() {
		return cashierID;
	}

	public String getCashierName() {
		return cashierName;
	}

	public String getPassword() {
		return password;
	}
	
	
	
	
}
