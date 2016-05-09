<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>Owner Page</title>
<style type="text/css">
#PageName {
	color: white;
	font-size: 250%;
	text-align: center;
	background-color: darkblue;
	font-variant: small-caps;
}
#LinkContent {
	float: left;
	border: 1px solid darkblue;
	width: 400px;
	margin-left: 13px;
	margin-top: 13px;
	margin-bottom: 13px;
}

#LinkName {
	color: darkblue;
	font-size: 150%;
	border-bottom: 2px solid darkblue;
	width: 300px;
	margin-left: 13px;
	margin_bottom: 20px;
}

#ContactBody {
	margin-left: 20px;
	margin-top: 20px;
	margin-bottom: 20px;
}

#Content {
    margin: 50px auto;
	border: 1px solid darkblue;
	width: 400px;
	padding: 10px;
}
button {
	margin-top: 3px;
	margin-left: 5px;
	margin-bottom: 5px;
}
div.fixed {
	position: fixed;
	bottom: 10px;
	right: 10px;
}

button {
	margin-top: 3px;
	margin-left: 5px;
	margin-bottom: 5px;
}
td.label {
	text-align: right;
}
input[type=submit]{
	padding: 5px;

}
</style>
</head>

<body>
	<div id="PageName">Your Orders</div>
	<div id="Content">
			<c:if test="${!empty message }">
				<div> ${message}</div>
			</c:if>
			<c:forEach items="${orderNum}" var="order">
			<form action="${pageContext.servletContext.contextPath}/RestOrders" method="post">
					<table>
						<tr>
							<td>Order Number: <input type="Submit" name="submit" value="${order}"/></td>
							<input type="hidden" name="orderNumber" id="orderNumber" value="${order }"/>
						</tr>
					</table>
			</form>
			</c:forEach>
			<c:if test="${! empty items}">
				<table>
					<tr>
						<td>Status:</td>
						<td>${status}</td>
					</tr>
					<tr>
						<form action="${pageContext.servletContext.contextPath}/RestOrders" method="post">
						<td><input type="radio" name="status" value="In Progress">In Progress</td>
						<td><input type="radio" name="status" value="Complete" >Complete</td>
						<input type="hidden" name="orderNumber" id="orderNumber" value="${orderNumber }"/>
						<td><input type="Submit" name="submit" value="Change Status" ></td>
						</form>
					</tr>
				</table>
				<table>
					<tr>
						<td>Qty</td>
						<td>Item</td>
						<td>Price</td>
					</tr>
			<c:forEach items="${items}" var="item">
					<tr>
						<td class = "label">${item.quantity}</td>
						<td class = "label">${item.item}</td>
						<td> @ </td>
						<td class = "label">${item.price}</td>
					</tr>
			</c:forEach>
			</table>
			<form action="${pageContext.servletContext.contextPath}/RestOrders" method="get">
					<input type="Submit" name="submit" value="Click to go Back"/>
			</form>
			</c:if>
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