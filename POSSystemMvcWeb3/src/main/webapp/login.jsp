<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cashier Login</title>
</head>
<body>

<div align="center"><h1>.: Login :.</h1>
<h3><c:out value="${message}"></c:out></h3>
<div align="center"><form action="login.action" method="post" >
	<label>Cashier ID</label> <br>
		<input type="text" name="cashier_ID" class="form-control" placeholder="Enter ID"><br>
	<label>Password</label> <br>
		<input type="password" name="password" class="form-control" placeholder="Password"><br>
	<br><button type="submit" class="btn btn-primary">Login</button>
	
</form>
</br>

</body>
</html>