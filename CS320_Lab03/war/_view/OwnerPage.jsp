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

.Address {
	font-size: 15px;
	font-color: black;
}
#Rests {
	float: left;
	border: 1px solid darkblue;
	width: 400px;
	margin-left: 13px;
	margin-top: 13px;
	margin-bottom: 13px;
}

#RestName {
	color: darkblue;
	font-size: 150%;
	border-bottom: 2px solid darkblue;
	width: 300px;
	margin-left: 13px;
	margin_bottom: 20px;
}

#Content {
	float: left;
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
</style>
</head>

<body>
		<div id="PageName">Owner Page</div>
	<div id="Content">
		<div id="Restaurants">
			<c:forEach items="${rest}" var="restaurant">
				<div id="Rests">
					<div id="RestName">${restaurant.name}</div>
					<table>
						<tr>
							<td class = "Address">${restaurant.address },</td>
							<td class = "Address">${restaurant.city },</td>
							<td class = "Address">${restaurant.zipCode }</td>
							<form action="${pageContext.servletContext.contextPath}/Restaurant" method="post">
								<input type="hidden" name="restaurant" id="restaurant" value="${restaurant.name}">
								<td><input type="submit" value="Click to view your page"></td>
							</form>
						</tr>
					</table>
				</div>
				<br>
			</c:forEach>
			<form action="${pageContext.servletContext.contextPath}/AddRestaurant" method="get">
					<input type="Submit" name="submit" value="Add A Restaurant"/>
			</form>
			<form action="${pageContext.servletContext.contextPath}/Homepage" method="get">
					<input type="Submit" name="submit" value="Click to go to Homepage"/>
			</form>
		</div>
	</div>

		<div class="fixed">
			<button>
				<a href="/lab03/Login">Logout</a>
			</button>
		</div>
</body>
</html>