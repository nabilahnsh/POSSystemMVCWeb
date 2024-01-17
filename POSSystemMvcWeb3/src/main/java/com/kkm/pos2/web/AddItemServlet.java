package com.kkm.pos2.web;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddItemServlet
 */
@WebServlet("/add-item.do")
public class AddItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Writer out = response.getWriter();
		//response.setContentType("text/html");
		String userName = "root";
		String password = "";
		String jdbcUrl = "jdbc:mysql://localhost:3306/pos2_db";
			
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		try (Connection conn = DriverManager.getConnection(jdbcUrl, userName, password)){
			//Load Driver
			String sqlQuery = "SELECT * FROM item";
			
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sqlQuery);
			
			out.write("<table border =5 width=50%>");
			out.write("<tr>");
			out.write("<th> item_code </th>");
			out.write("<th> price </th>");
			out.write("<th> description </th>");
			out.write("<th> type </th>");
			out.write("<th> taxable </th>");
			out.write("</tr>");
			//read result
			while(rs.next()) {
				out.write("<tr>");
				out.write("<td>"+rs.getString("item_code")+"</td>");
				out.write("<td>"+rs.getInt("price")+"</td>");
				out.write("<td>"+rs.getString("description")+"</td>");
				out.write("<td>"+rs.getString("type")+"</td>");
				//out.write("<td>"+rs.getString("taxable")+"</td>");
				if(Integer.parseInt(rs.getString("taxable")) == 1) {
					out.write("<td> yes </td>");
				} else {
					out.write("<td> no </td>");
				}
				out.write("</tr>");
				
				
			}
			out.write("</table>");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String item_code = request.getParameter("item_code");
		double price = Double.parseDouble(request.getParameter("price"));
		String description = request.getParameter("description");
		String type = request.getParameter("type");
		int taxable = Integer.parseInt(request.getParameter("taxable"));
		
		String userName = "root";
		String password = "";
		String jdbcUrl = "jdbc:mysql://localhost:3306/pos2_db";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection(jdbcUrl, userName, password)){
			
			
			String sql = "INSERT INTO item(item_code, price, description, type, taxable) values (?,?,?,?,?)";
			PreparedStatement stm = conn.prepareStatement(sql);
			
			stm.setString(1, item_code);
			stm.setDouble(2, price);
			stm.setString(3, description);
			stm.setString(4, type);
			stm.setInt(5, taxable);

			stm.executeUpdate();
			
		}catch (SQLException e1) {
			
		}
	}

}
