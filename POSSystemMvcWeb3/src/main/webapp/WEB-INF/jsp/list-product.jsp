<%@page import="com.kkm.pos2.domain.Item"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product List</title>
</head>
<body>
<div align="center"><h1>.: Product List View :.</h1>

				
			<div align="center"><table border =5 width=50%>
			<tr>
			<th> item_code </th>
			<th> price </th>
			<th> description </th>
			<th> type </th>
			<th> taxable </th>
			<th> action </th>
			</tr>
			<c:forEach items="${data_item}" var = "i">
		
				<tr>
				<td><c:out value = "${i.itemCode}"></c:out></td>
				<td><c:out value = "${i.itemPrice}"></c:out></td>
				<td><c:out value = "${i.description}"></c:out></td>
				<td><c:out value = "${i.type}"></c:out></td>
				<td><c:out value = "${i.taxable}"></c:out></td>
				<td><div align="center"><a href="pos.do?action=form-qty&item_code=<c:out value = '${i.itemCode}'/>">Add</a>
				</td>
				
								
				</tr>
				
			</c:forEach>
			
			</table>
	
</body>
</html>