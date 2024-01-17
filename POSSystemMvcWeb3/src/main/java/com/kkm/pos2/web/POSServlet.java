package com.kkm.pos2.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kkm.pos2.domain.CashPayment;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Payment;
import com.kkm.pos2.domain.QrisPayment;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.repository.impl.CashierRepositoryMySQL;
import com.kkm.pos2.repository.impl.ItemRepositoryMySQL;
import com.kkm.pos2.repository.impl.SaleItemRepositoryMySQL;
import com.kkm.pos2.repository.impl.SaleRepositoryMySQL;

/**
 * Servlet implementation class POSServlet
 */
@WebServlet("/pos.do")
public class POSServlet extends HttpServlet {
	SaleRepositoryMySQL repoSale = new SaleRepositoryMySQL();
	ItemRepositoryMySQL repoItem = new ItemRepositoryMySQL();
	CashierRepositoryMySQL repoCashier = new CashierRepositoryMySQL();
	List<SaleItem> saleItems = new ArrayList<SaleItem>();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public POSServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		RequestDispatcher dispatcher = null;
		// if
		// (session.getAttribute("cashier_login").equals(session.getAttribute("cashier_contextID")))
		// {
		String action = request.getParameter("action");
		try {
			if (action.equals("")) {
				//response.sendRedirect("homepage.jsp");
				dispatcher = request.getRequestDispatcher("homepage.jsp");
				dispatcher.forward(request, response);
			} else if (action.equals("show")) {
				List<Item> itemList;
				itemList = repoItem.findAll();
				request.setAttribute("data_item", itemList);
				dispatcher = request.getRequestDispatcher("WEB-INF/jsp/list-product.jsp");
				dispatcher.forward(request, response);
			} else if (action.equals("form-qty")) {
				String item_code = request.getParameter("item_code");
				Item item = repoItem.findByCode(item_code);

				request.setAttribute("item", item);
				dispatcher = request.getRequestDispatcher("WEB-INF/jsp/quantity-product.jsp");
				dispatcher.forward(request, response);
			} else if (action.equals("add")) {
				String item_code = request.getParameter("item_code");
				double price = Double.parseDouble(request.getParameter("price"));
				String description = request.getParameter("description");
				String type = request.getParameter("type");
				boolean taxable = Boolean.parseBoolean(request.getParameter("taxable"));
				Item newItem = new Item(item_code, price, description, type, taxable);
				int quantity = Integer.parseInt(request.getParameter("quantity"));

				List<SaleItem> cart;
				Sale sale = (Sale) session.getAttribute("sale");
				Cashier cashier_context = (Cashier) session.getAttribute("cashier_context");

				// String sale_number = "";
				if (session.getAttribute("cart") != null) {
					cart = (List<SaleItem>) session.getAttribute("cart");
					boolean sameItem = sameItem(cart,request);
//					for (SaleItem c : cart) {
//						if (c.getItem().getItemCode().equals(item_code)) {
//							c.setQuantity(c.getQuantity() + quantity);
//						}
//					}
					if(!sameItem) {
						SaleItem saleItem = new SaleItem(newItem, quantity);
						cart.add(saleItem);
						sale.addSaleItem(saleItem);
					}
					
					
				} else {

					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
					String sale_number = formatter.format(date);

					sale = new Sale(sale_number, cashier_context);
					session.setAttribute("sale", sale);

					cart = new ArrayList<SaleItem>();
					// session.setAttribute("cart", cart);

					SaleItem saleItem = new SaleItem(newItem, quantity);
					cart.add(saleItem);
					sale.addSaleItem(saleItem);

				}

				session.setAttribute("cart", cart);

				dispatcher = request.getRequestDispatcher("shopping-cart.jsp");
				dispatcher.forward(request, response);

			}

			else if (action.equals("check-out")) {
				String payment_value = request.getParameter("payment");

				Sale sale = (Sale) session.getAttribute("sale");
				List<SaleItem> cart = (List<SaleItem>) session.getAttribute("cart");
				
				Payment payment = null;
				if (payment_value != null) {
					if (payment_value.equals("cash_payment")) {
						double cashInHand = Double.parseDouble(request.getParameter("cashInHand"));
						payment = new CashPayment(cashInHand);

					} else {
						payment = new QrisPayment();

					}
					sale.setPayment(payment);
				}
				System.out.println(payment_value);

				repoSale.save(sale);
				
				
				Sale saleReceipt = repoSale.findAllWebByNumber(sale.getSaleNumber());
				request.setAttribute("saleReceipt", saleReceipt);
				
				session.removeAttribute("cart");
				session.removeAttribute("sale");

				dispatcher = request.getRequestDispatcher("WEB-INF/jsp/receipt.jsp");
				dispatcher.forward(request, response);
			} else if (action.equals("order-history")) {
				List<Sale> listSale = repoSale.findAllWeb();
				request.setAttribute("data_sale", listSale);

				dispatcher = request.getRequestDispatcher("WEB-INF/jsp/order-history.jsp");
				dispatcher.forward(request, response);
			} else if (action.equals("remove")) {
				String item_code = request.getParameter("item_code");
				if (item_code != null) {
					List<SaleItem> cart = (List<SaleItem>) session.getAttribute("cart");
					if (cart != null) {
						for (SaleItem c : cart) {
							if (c.getItem().getItemCode() == item_code) {
								cart.remove(cart.indexOf(c));
								break;
							}
						}
					}
					response.sendRedirect("pos.do?action=add");
				}
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		} else {
//			String message = "Anda belum login atau session expired!!";
//			request.setAttribute("message", message);
//			dispatcher = request.getRequestDispatcher("login.jsp");
//		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

	private boolean sameItem(List<SaleItem> cart, HttpServletRequest request) {
		// List<SaleItem> cartList = (List<SaleItem>) session.getAttribute("cart");
		String item_code = request.getParameter("item_code");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		for (SaleItem c : cart) {
			if (c.getItem().getItemCode().equals(item_code)) {
				c.setQuantity(c.getQuantity() + quantity);
				return true;
			}

		}
		return false;
	}

}
