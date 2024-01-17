package com.kkm.pos2.web;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kkm.pos2.domain.Item;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.repository.impl.ItemRepositoryMySQL;

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/item-list.do")
public class ItemServlet extends HttpServlet {
	ItemRepositoryMySQL repo = new ItemRepositoryMySQL();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String action = request.getParameter("action");
		if(action == null) {
			List<Item> itemList;
			itemList = repo.findAll();
			request.setAttribute("data_item", itemList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/item-list.jsp");
			dispatcher.forward(request, response);
		}else if(action.equals("detail")) {
			String item_code = request.getParameter("item_code");
			Item item = repo.findByCode(item_code);
			
			request.setAttribute("detail_item", item);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/item-detail.jsp");
			dispatcher.forward(request, response);
		}else if(action.equals("edit")) {
			String item_code = request.getParameter("item_code");
			double price = Double.parseDouble(request.getParameter("price"));
			String description = request.getParameter("description");
			String type = request.getParameter("type");
			boolean taxable = Boolean.parseBoolean(request.getParameter("taxable"));
			Item newItem = new Item(item_code, price, description, type, taxable);
			
			repo.editItem(newItem);
			response.sendRedirect("item-list.do");
		} else if(action.equals("save")) {
			String item_code = request.getParameter("item_code");
			double price = Double.parseDouble(request.getParameter("price"));
			String description = request.getParameter("description");
			String type = request.getParameter("type");
			boolean taxable = Boolean.parseBoolean(request.getParameter("taxable"));
			
			Item newItem = new Item(item_code, price, description, type, taxable);
			repo.addItem(newItem);
			response.sendRedirect("item-list.do");
		} else if (action.equals("edit-form")) {
			String item_code = request.getParameter("item_code");
			Item item = repo.findByCode(item_code);
			
			request.setAttribute("item", item);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/item-edit.jsp");
			dispatcher.forward(request, response);
		} else if (action.equals("delete")) {
			String item_code = request.getParameter("item_code");
			repo.deleteItem(item_code);
			response.sendRedirect("item-list.do");
		}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}
	
}
