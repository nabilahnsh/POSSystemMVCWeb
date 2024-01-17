<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div align="center"><form method="post" action="pos.do?action=add">
	<label>Item Code</label><br>
	<input name="item_code" type="text" value="${item.itemCode}"/><br>
	<label>Price</label><br>
	<input name="price" type="text" value="${item.itemPrice}"/><br>
	<label>Description</label><br>
	<input name="description" type="text" value="${item.description}"/><br>
	<label>Type</label><br>
	<input name="type" type="text" value="${item.type}"/><br>
	<label>Taxable</label><br>
	<input name="taxable" type="text" value="${item.taxable}"/><br>
	<label>Quantity</label><br>
	<input type="text" name="quantity"/><br>
	<br><input type="submit" value="Add"/>
</body>
</html>