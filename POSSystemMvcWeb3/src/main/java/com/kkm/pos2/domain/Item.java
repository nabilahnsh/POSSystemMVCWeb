package com.kkm.pos2.domain;

public class Item {
	
	private String itemCode;
	private double itemPrice;
	private String description;
	private String type;
	private boolean taxable;
	
	public Item(String itemCode, double price, String description, String type, boolean taxable) {
		this.itemCode = itemCode;
		this.itemPrice = price;
		this.description = description;
		this.type = type;
		this.taxable = taxable;
	}
	
	

	public String getItemCode() {
		return itemCode;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}


	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}



	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public void setType(String type) {
		this.type = type;
	}



	public boolean isTaxable() {
		return taxable;
	}


}
