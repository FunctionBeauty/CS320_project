<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>Recent Orders</title>
<style type="text/css">
#PageName {
	color: white;
	font-size: 250%;
	text-align: center;
	background-color: darkblue;
	font-variant: small-caps;
}
#Content {
    margin: 50px auto;
	border: 1px solid darkblue;
	width: 400px;
	padding: 10px;
}
div.fixed {
	position: fixed;
	bottom: 10px;
	right: 10px;
}
input[type=submit]{
	padding: 5px;

}
</style>
</head>

<body>
	<div id="PageName">Your Orders</div>
	<div id="Content">
			<c:forEach items="${orderNum}" var="order">
			<form action="${pageContext.servletContext.contextPath}/RecentOrders" method="post">
					<table>
						<tr>
							<td>Order Number: <input type="Submit" name="submit" value="${order}"/></td>
							<input type="hidden" name="orderNumber" id="orderNumber" value="${order }"/>
						</tr>
					</table>
			</form>
			</c:forEach>
			<table>
			<c:if test="${! empty status}">
				<tr>
					<td>Status: </td>
					<td> ${status}</td>
				</tr>
				<tr>
					<td>Qty</td>
					<td> Item</td>
					<td>Price</td>
				</tr>
				</c:if>
			<c:forEach items="${items}" var="item">
					<tr>
						<td>${item.quantity}</td>
						<td>${item.item}</td>
						<td>$ ${item.price }</td>
					</tr>
			</c:forEach>
			<c:if test ="${! empty price }">
					<tr>
						<td>Total: </td>
						<td>$ ${price}</td>
					</tr>
			</c:if>
			</table>
			<form action="${pageContext.servletContext.contextPath}/Homepage" method="get">
					<input type="Submit" name="submit" value="Click to go to Homepage"/>
			</form>
	</div>

		<div class="fixed">
			<button>
				<a href="/lab03/Login">Logout</a>
			</button>
		</div>
</body>
</html>