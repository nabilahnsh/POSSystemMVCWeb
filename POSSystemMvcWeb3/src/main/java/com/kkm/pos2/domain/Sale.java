package com.kkm.pos2.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



public class Sale {
	private String saleNumber;
	private Date transDate = new Date();
	private List<SaleItem> saleItems = new ArrayList<SaleItem>();
	private Cashier cashier;
	private Payment payment;
	private double saleTax;
	
	double totalPayment = 0;

	
	public Payment getPayment() {
		return payment;
	}
	
	
	
	public double getSaleTax() {
		return saleTax;
	}



	public void setSaleTax(double saleTax) {
		this.saleTax = saleTax;
	}



	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Sale(String saleNumber, Cashier cashier) {
		this.saleNumber = saleNumber;
		this.cashier = cashier;
		this.transDate = new Date();
	}

	public void addSaleItem(SaleItem saleItem) {	
		saleItems.add(saleItem);	
	}

	public String getSaleNumber() {
		return saleNumber;
	}
	
	

	public Date getTransDate() {
		return transDate;
	}

	public List<SaleItem> getSaleItems() {
//		List<SaleItem> clonedList = new ArrayList<SaleItem>();
//		clonedList.addAll(saleItems);
		return new ArrayList<SaleItem>(saleItems);
	}
	
	public void setSaleItems(List<SaleItem> saleItems) {
		this.saleItems = saleItems;
	}

	public Cashier getCashier() {
		return cashier;
	}
	
	public double grandTotal() {
		double totalPrice = 0;
		Iterator<SaleItem> it = saleItems.iterator();
		while(it.hasNext()) {
			SaleItem si = it.next();
			totalPrice = totalPrice + si.totalPrice();
			}
		return totalPrice;
	}
	
//	public double grandTotalWeb() {
//		double totalP=0;
//		Iterator<SaleItem> it = saleItems.iterator();
//		while(it.hasNext()) {
//			SaleItem si = it.next();
//			totalP += si.totalPrice();
//			}
//		return totalP;
//	}
	
	public double totalTaxPayment() {
		double totalTaxPayment = 0;
		double tax = 0;
		Iterator<SaleItem> it = saleItems.iterator();		
		while(it.hasNext()) {
			SaleItem si = it.next();
			if(si.getItem().isTaxable() == true) {
			totalTaxPayment = totalTaxPayment + si.totalPrice();
			tax = totalTaxPayment * Tax.tax;
			}
			} return tax;
	}
	
//	public double totalPayment() {
//		totalPayment = totalPrice + tax ;
//		return totalPayment;
//	}
	
	public void finish() {
		System.out.println("Finish Transaction!");
	}

	
	
	
	
	
	
}
