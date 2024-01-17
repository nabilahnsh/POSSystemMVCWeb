package com.kkm.pos2.domain;

public class CashPayment extends Payment{

private double cashInHand;
	


	public CashPayment(double cashInHand) {
		super();
		this.cashInHand = cashInHand;
	}


	public double getCashInHand() {
		return cashInHand;
	}

	
	public double change(){
		return cashInHand - this.amount;
	}


	@Override
	public void validate() {
		this.amount = sale.grandTotal() + sale.totalTaxPayment();
		this.isPay = true;
	}


	@Override
	public void finishSale() {
		System.out.println("Payment (Cash) :" + cashInHand);
		System.out.println("Change :" + change() + "\n");
		
	}
	
	
	}
