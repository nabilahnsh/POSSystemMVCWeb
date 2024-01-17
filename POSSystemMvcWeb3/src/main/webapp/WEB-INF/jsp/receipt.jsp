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
	<div align="center">
		<h1>.: Receipt :.</h1>

		<table border=5 width=60%>
			<thead>
				<tr>
					<th colspan="1"><font size="5">sale_number</th>
					<th colspan="7"><font size="5">${saleReceipt.getSaleNumber()}</th>
				</tr>
				<tr>
					<th>item_code</th>
					<th>price</th>
					<th>description</th>
					<th>type</th>
					<th>taxable</th>
					<th>quantity</th>
					<th>total</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="cart" items="${saleReceipt.getSaleItems()}">
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
				<th colspan="7"><font size="4">${saleReceipt.grandTotal()}</th>
				<tr>
					<th colspan="1"><font size="4">tax</th>
					<th colspan="7"><font size="4">${saleReceipt.totalTaxPayment()}</th>
				</tr>
				<tr>
					<th colspan="1"><font size="4">total payment</th>
					<th colspan="7">${saleReceipt.grandTotal() + saleReceipt.getSaleTax()}</th>
				</tr>
				<tr>
					<th colspan="1"><font size="4">payment</th>

					<c:choose>
						<c:when
							test="${saleReceipt.payment['class'].simpleName == 'CashPayment'}">
							<th colspan="3"><font size="4">cash</th>
							<th colspan="4"><label>cash :
									${saleReceipt.getPayment().getCashInHand()}</label></th>
							<tr>
								<th colspan="1"><font size="4">change</th>
								<th colspan="7"><font size="4">${saleReceipt.getPayment().getCashInHand() - (saleReceipt.grandTotal() + saleReceipt.getSaleTax())}</th>
							</tr>
						</c:when>
						<c:otherwise>
							<th colspan="3"><font size="4">qris</th>
							<th colspan="4"><label>amount :
									${saleReceipt.grandTotal() + saleReceipt.getSaleTax()}</label>

							</th>

						</c:otherwise>
					</c:choose>
				</tr>
			</tbody>
		</table>
		<div align="center">
			<a href="homepage.jsp">Home</a>
</body>
</html>