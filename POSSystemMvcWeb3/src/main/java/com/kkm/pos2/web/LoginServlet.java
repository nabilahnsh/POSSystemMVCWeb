package com.kkm.pos2.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.repository.impl.CashierRepositoryMySQL;
import com.kkm.pos2.repository.impl.SaleRepositoryMySQL;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.action")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CashierRepositoryMySQL repoCashier = new CashierRepositoryMySQL();
	SaleRepositoryMySQL repo = new SaleRepositoryMySQL();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("pos.do");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		
			String id = request.getParameter("cashier_ID");
			String pass = request.getParameter("password");
			HttpSession session = request.getSession();

			Cashier cashier = repoCashier.cashierLogin(id,pass);
			//session.setAttribute("cashier_login", cashier.getCashierID());
			session.setAttribute("cashier_context", cashier);
			if(cashier != null) {
				session.setAttribute("cashier_contextID", cashier.getCashierID());

				response.sendRedirect("homepage.jsp");
			} else{
				out.println("ID and Password is wrong!!");
			}
			
	}

}
