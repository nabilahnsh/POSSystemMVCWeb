<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order History</title>
</head>
<body>
	<div align="center">
		<h1>.: Order History :.</h1>
		<table border=5 width=60%>
			<thead>
				<c:forEach items="${data_sale}" var="sale">
					<tr>
						<th colspan="1"><font size="4">sale_number</th>
						<th colspan="7"><font size="4">${sale.getSaleNumber()}</th>
					</tr>
					<tr>
						<th colspan="1"><font size="4">cashier_name</th>
						<th colspan="7"><font size="4">${sale.getCashier().getCashierName()}</th>
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
					<c:forEach items="${sale.getSaleItems()}" var="item"
						varStatus="loop">
						<tr>
							<td>${item.getItem().getItemCode()}</td>
							<td>${item.getItem().getItemPrice()}</td>
							<td>${item.getItem().getDescription()}</td>
							<td>${item.getItem().getType()}</td>
							<td>${item.getItem().taxable}</td>
							<td>${item.getQuantity()}</td>
							<td>${item.totalPrice()}</td>
						</tr>
					</c:forEach>
			</thead>
			<tbody>
				<th colspan="1"><font size="4">subtotal</th>
				<th colspan="7"><font size="4">${sale.grandTotal()}</th>
				<tr>
					<th colspan="1"><font size="4">tax</th>
					<th colspan="7"><font size="4">${sale.getSaleTax()}</th>
				</tr>
				<tr>
					<th colspan="1"><font size="4">total payment</th>
					<th colspan="7">${sale.grandTotal() + sale.getSaleTax()}</th>
				</tr>
				<tr>
					<th colspan="1"><font size="4">payment</th>

					<c:choose>
						<c:when
							test="${sale.payment['class'].simpleName == 'CashPayment'}">
							<th colspan="3"><font size="4">cash</th>
							<th colspan="4"><label>cash :
									${sale.getPayment().getCashInHand()}</label></th>
							<tr>
								<th colspan="1"><font size="4">change</th>
								<th colspan="7"><font size="4">${sale.getPayment().getCashInHand() - (sale.grandTotal() + sale.getSaleTax())}</th>
							</tr>
						</c:when>
						<c:otherwise>
							<th colspan="3"><font size="4">qris</th>
							<th colspan="4"><label>amount : ${sale.grandTotal() + sale.getSaleTax()}</label>
							</th>

						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<th colspan="7"><font size="4">#############################################################################################################</th>
				</tr>

			</tbody>
			</c:forEach>


			</tbody>
		</table>
		<div align="center">
			<a href="homepage.jsp">Home</a>
</body>
</html>