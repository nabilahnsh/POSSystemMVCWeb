package com.kkm.pos2.domain;

public class QrisPayment extends Payment {
	
	private double amount;
	
	
	
	public QrisPayment() {
		super();
	}

	public QrisPayment(double amount) {
		super();
		this.amount = amount;
	}

	public void generateQR() {
		System.out.println("Please Scan This QR . . .");
	}

	@Override
	public void validate() {
		this.amount = amount;
		this.isPay = true;
	}

	@Override
	public void finishSale() {
		System.out.println("Payment (QRIS) : " + amount + "\n");
		
	}
	
}
