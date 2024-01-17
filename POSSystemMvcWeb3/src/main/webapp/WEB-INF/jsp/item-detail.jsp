<%@page import="com.kkm.pos2.domain.Item"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Item Detail</title>
</head>
<style>
th, td {
padding: 5px;
text-align: left;
}
</style>
<body>
<div align="center"><h1>.: Item Detail :.</h1>
<div align="center"><table border =5>
	<tr>
		<th>item code:</th>
		<td><input name="item_code" type="text" value="${detail_item.itemCode}"/></td>
	</tr>	
	<tr>
		<th>price:</th>
		<td><input name="price" type="text" value="${detail_item.itemPrice}"/></td>
	</tr>
	<tr>
		<th>description:</th>
		<td><input name="description" type="text" value="${detail_item.description}"/></td>
	</tr>
	<tr>
		<th>type:</th>
		<td><input name="type" type="text" value="${detail_item.type}"/></td>
	</tr>
	<tr>
		<th>taxable:</th>
		<td><input name="taxable" type="text" value="${detail_item.taxable}"/></td>
	</tr>
</table>


</br>
</body>
</html>