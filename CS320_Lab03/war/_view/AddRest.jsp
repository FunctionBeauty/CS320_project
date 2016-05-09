<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>Add Restaurant</title>
<style type="text/css">
.error {
	color: red;
}

td.label {
	text-align: right;
}
#PageName {
	color: white;
	font-size: 250%;
	text-align: center;
	background-color: darkblue;
	font-variant: small-caps;
}
#createAccount {
	margin: 50px auto;
	border: 1px solid darkblue;
	width: 400px;
	padding: 10px;
}
</style>
</head>

<body>
	<c:if test="${! empty errorMessage}">
		<div class="error">${errorMessage}</div>
	</c:if>
	<div id="PageName">Add A Restaurant</div>
	<form action="${pageContext.servletContext.contextPath}/AddRestaurant"
		method="post">
		<div id="createAccount">
			<table>
				<tr>
					<td class="label">Restaurant Name:</td>
					<td><input type="text" name="restName" size="12"
						value="${restName}" required /></td>
				</tr>
				<tr>
					<td class="label">Street:</td>
					<td><input type="text" name="street" size="12"
						value="${street}" required /></td>
				</tr>
				<tr>
					<td class="label">City, State:</td>
					<td><input type="text" name="city" size="12"
						value="${city}" required /></td>
				</tr>
				<tr>
					<td class="label">Zipcode:</td>
					<td><input type="text" name="zipcode" size="12"
						value="${zipcode}" required /></td>
				</tr>
			</table>
			<input type="Submit" name="submit" value="Add this restaurant" />
		</div>
	</form>
</body>
</html>