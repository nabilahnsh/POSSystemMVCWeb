package com.kkm.pos2.test;

import java.util.Iterator;

import com.kkm.pos2.domain.CashPayment;
import com.kkm.pos2.domain.Payment;
import com.kkm.pos2.domain.QrisPayment;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.exception.UseCaseException;
import com.kkm.pos2.usecase.ProcessSaleUseCase;

public class SaleTest {
	
	public static void main(String[] args) throws UseCaseException  {
		
		ProcessSaleUseCase saleUseCase = new ProcessSaleUseCase();
		
		//Sale1
		saleUseCase.createNewSale("#1", "01");
		saleUseCase.addSaleItem("001", 2);
		saleUseCase.addSaleItem("002", 1);
		saleUseCase.addSaleItem("003", 2);
		saleUseCase.getSale().makePayment(saleUseCase.cash(150000)).finishSale();

		
		//Sale2
		saleUseCase.createNewSale("#2", "02");
		saleUseCase.addSaleItem("004", 10);
		saleUseCase.addSaleItem("003", 4);
		saleUseCase.getSale().makePayment(saleUseCase.qris()).finishSale();

		
		//Sale3
		saleUseCase.createNewSale("#3", "01");
		saleUseCase.addSaleItem("001", 2);
		saleUseCase.addSaleItem("004", 4);
		saleUseCase.addSaleItem("002", 1);
	 	saleUseCase.getSale().makePayment(saleUseCase.cash(120000)).finishSale();

	
	
	}

}
