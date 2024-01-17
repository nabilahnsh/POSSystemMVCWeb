package com.kkm.pos2.domain;

public abstract class Payment {
	protected double amount;
	protected Sale sale;
	protected boolean isPay = false;
	
	

		
		

		public void setAmount(double amount) {
		this.amount = amount;
	}

		public double getAmount() {
		return this.amount;
	}
		
		public void setSale(Sale sale) {
			this.sale = sale;
		}

		public abstract void validate();
		
		public abstract void finishSale();
	
	
}
