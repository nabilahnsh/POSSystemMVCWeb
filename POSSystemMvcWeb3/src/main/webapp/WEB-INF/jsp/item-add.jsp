<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Item</title>
</head>
<body>
<div align="center"><h1>.: Add Item :.</h1>
<div align="center"><form method="post" action="item-list.do?action=save">
	<label>Item Code</label><br>
	<input type="text" name="item_code"/><br>
	<label>Price</label><br>
	<input type="text" name="price"/><br>
	<label>Description</label><br>
	<input type="text" name="description"/><br>
	<label>Type</label><br>
	<input type="text" name="type"/><br>
	<label>Taxable</label><br>
	<input type="text" name="taxable"/><br>
	<br><input type="submit" value="Save"/>
</form>
</br>
</body>
</html>