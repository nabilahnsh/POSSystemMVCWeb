package com.kkm.pos2.usecase;

import java.util.Iterator;

import com.kkm.pos2.domain.CashPayment;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Payment;
import com.kkm.pos2.domain.QrisPayment;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.exception.UseCaseException;
import com.kkm.pos2.repository.CashierRepository;
import com.kkm.pos2.repository.ItemRepository;
import com.kkm.pos2.repository.SaleItemRepository;
import com.kkm.pos2.repository.SaleRepository;
import com.kkm.pos2.repository.impl.CashierRepositoryMySQL;
import com.kkm.pos2.repository.impl.ItemRepositoryDummy;
import com.kkm.pos2.repository.impl.ItemRepositoryFile;
import com.kkm.pos2.repository.impl.ItemRepositoryMySQL;
import com.kkm.pos2.repository.impl.SaleItemRepositoryMySQL;
import com.kkm.pos2.repository.impl.SaleRepositoryDummy;
import com.kkm.pos2.repository.impl.SaleRepositoryMySQL;

public class ProcessSaleUseCase {
	private Sale sale;
	private ItemRepository itemRepository;
	private SaleRepository saleRepository;
	private SaleItemRepository saleItemRepository;
	private CashierRepository cashierRepository;
	
	public ProcessSaleUseCase() {
		itemRepository = new ItemRepositoryMySQL();
		saleRepository = new SaleRepositoryMySQL();
		cashierRepository = new CashierRepositoryMySQL();
		saleItemRepository = new SaleItemRepositoryMySQL();
	}
	
	public ProcessSaleUseCase createNewSale(String saleNumber, String cashierID) throws UseCaseException{
		try {
			sale = new Sale(saleNumber, cashierRepository.findCashierById(cashierID));
		} catch (RepositoryException e) {
			throw new UseCaseException(e.getMessage());
		}
		return this;
	}
	
	public ProcessSaleUseCase addSaleItem(String itemCode, int quantity) throws UseCaseException{
		try {
			sale.addSaleItem(saleItemRepository.save(itemRepository.findByCode(itemCode), quantity));
		} catch (RepositoryException e) {
			throw new UseCaseException(e.getMessage());
		} 
		return this;
	}
	
	public ProcessSaleUseCase makePayment(Payment payment) {
		sale.setPayment(payment);
		payment.setSale(sale);
		return this;	
		}
	
	

	public ProcessSaleUseCase getSale() {
		System.out.println("=============================================================");
		System.out.println("Sale Number : " + sale.getSaleNumber());
		System.out.println("Cashier : " + sale.getCashier().getCashierName());
		System.out.println("Date : "+ sale.getTransDate().toString());
		
		System.out.println("Item : ");
		Iterator<SaleItem> itSaleItem = sale.getSaleItems().iterator();
		while(itSaleItem.hasNext()){
				SaleItem saleItem = itSaleItem.next();
				System.out.println("Item Code : "+ saleItem.getItem().getItemCode() + " | Description : "+ saleItem.getItem().getDescription()
						+" | Type : "+saleItem.getItem().getType()+" | Price : "+saleItem.getItem().getItemPrice()+" | Quantity : "+saleItem.getQuantity()
						+" | Total Price : "+saleItem.totalPrice());
			}
		
		System.out.println();
		System.out.println("Grand Total : "+ sale.grandTotal());
		System.out.println("Tax : "+ sale.totalTaxPayment());
		System.out.println("Total Price + Tax : "+ sale.getPayment().getAmount()+ "\n");
		return this;
	}
	public void finishSale()  throws UseCaseException {
		System.out.println("\n" + "=============================================================");
		System.out.println("Receipt After Payment");
		sale.getPayment().validate();
			
			try {
				saleRepository.save(sale);
			} catch (RepositoryException e) {
				throw new UseCaseException(e.getMessage());
			}

		getSale();
		sale.getPayment().finishSale();
		System.out.println("=============================================================" + "\n");	
	}
	
	public Payment qris() {
		return new QrisPayment();
	}
	
	public Payment cash(int amount) {
		return new CashPayment(amount);
	}
	
	public void addCashier(Cashier cashier) throws UseCaseException{
		try {
			cashierRepository.save(cashier);
		} catch (RepositoryException e) {
			throw new UseCaseException(e.getMessage());
		}
	}
	
	public Sale findByNumber(String number) throws UseCaseException{
		try {
			return saleRepository.findByNumber(number);
		} catch (RepositoryException e) {
			throw new UseCaseException(e.getMessage());
		}
		
	}
	
	
}
