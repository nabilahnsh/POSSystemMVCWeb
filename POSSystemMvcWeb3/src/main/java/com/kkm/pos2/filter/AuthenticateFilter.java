package com.kkm.pos2.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
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

@WebFilter("/authenticateFilter")
public class AuthenticateFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Authenticate Filter -- executed");
	
		HttpSession session = ((HttpServletRequest)request).getSession();
		String cashier_login = (String) session.getAttribute("cashier_login");
		String cashier_contextID = (String) session.getAttribute("cashier_contextID");
		
		RequestDispatcher dispatcher = null;
		if("01".equals(cashier_contextID)) {
			chain.doFilter(request, response);
			
			//dispatcher = request.getRequestDispatcher("login.jsp");
		} else {
			String message = "Anda belum login atau Session expire !";
			request.setAttribute("message", message);
			((HttpServletResponse) response).sendRedirect("login.jsp");
			
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
