<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping Cart</title>
</head>
<body>
<div align="center"><h1>.: Sale Cart :.</h1>
<div align="center"><a href="pos.do?action=show">Add Product</a>
<c:if test="${sessionScope.cart != null}">
<table border =5 width=60%>
        <thead>
        	<tr>
        		<th colspan="1"><font size="5">sale_number</th>
    			<th colspan="6"><font size="5">${sessionScope.sale.getSaleNumber()}</th>
        	</tr>
            <tr>
                <th> item_code </th>
				<th> price </th>
				<th> description </th>
				<th> type </th>
				<th> taxable </th>
                <th> quantity </th>
                <th> total </th>
               
            </tr>
        </thead>
        <tbody>
            <c:forEach var="cart" items="${sessionScope.cart}">
                <tr>
                    <td>${cart.getItem().getItemCode()}</td>
                    <td>${cart.getItem().getItemPrice()}</td>
                    <td>${cart.getItem().getDescription()}</td>
                    <td>${cart.getItem().getType()}</td>
                    <td>${cart.getItem().taxable}</td>
                    <td>${cart.getQuantity()}</td>
                    <td>${cart.totalPrice()}</td>
                    
                </tr>
            </c:forEach>  
            <th colspan="1"><font size="4">subtotal</th>
    		<th colspan="6"><font size="4">${sessionScope.sale.grandTotal()}</th>
    		<tr>
    		<th colspan="1"><font size="4">tax</th>
    		<th colspan="6"><font size="4">${sessionScope.sale.totalTaxPayment()}</th>
    		</tr>
    		<tr>
    		<th colspan="1"><font size="4">total payment</th>
    		<th colspan="6">${sessionScope.sale.grandTotal() + sessionScope.sale.totalTaxPayment()}</th>   
			</tr>
			<form method="post" action="pos.do?action=check-out">
				<tr> 
				<th colspan="1"><font size="4">payment</th>
        		<th colspan="3">
        			<input type="radio" name="payment" value="cash_payment" />cash
					<input type="radio" name="payment" value="qris_payment" />qris
        		</th>
        		<th colspan="3">
        			<label>cash</label>
        			<input type="text" name="cashInHand"/></th>
        		</th>
			</tr>
			
			
			       
        </tbody>
    </table>
    <br><input type="submit" value="Checkout"/>
    </form>
    </c:if>
</body>
</html>