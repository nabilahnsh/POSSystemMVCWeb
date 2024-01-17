package com.kkm.pos2.domain;


public class SaleItem {
	
	private int quantity;
	private double price;
	private Item item;
	
	public SaleItem(Item item, int quantity) {
		this.quantity = quantity;
		this.item = item;
		this.price = item.getItemPrice();
		}
	

	public SaleItem(Item item) {
		super();
		this.item = item;
	}



	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
		this.price = item.getItemPrice();
	}

	public double getPrice() {
		return price;
	}

	public double totalPrice() {
		return price * quantity;
	}
	
	
	
	
	
	
	

}
