<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>Track N Snack Home</title>
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
	float: left;
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

.error {
	color: red;
}
.Address {
	font-size: 15px;

}
#Restaurants {
	float: left;
	border: 3px solid darkblue;
	width: 50%;
	margin-left: 150px;
	margin-top: 13px;
	margin-bottom: 13px;
	font-size: 150%;
}

#results {
	margin-top: 20px;
	margin-left: 150px;
	width: 50%;
	color: white;
	font-size: 200%;
	text-align: center;
	background-color: darkblue;
	border-top: 3px solid darkblue;
	border-bottom: 3px solid darkblue;
	font-variant: small-caps;
	float: left;
	
}

#resultList {
	font-size: 150%;
	text-align: center;
}

td.label {
	text-align: right;
}
</style>
</head>

<body>

	<c:if test="${! empty errorMessage}">
		<div class="error">${errorMessage}</div>
	</c:if>


	<div id="PageName">Track N Snack</div>
	<div id="Content">
		<div id="LinkContent">
			<div id="LinkName">Search</div>
			<table>
				<tr>
					<form action="${pageContext.servletContext.contextPath}/Homepage"
						method="post">
						<td class="label"></td>
						<td><input type="search" name="search" size="12" placeholder="Search"/></td>
						<td><input type="Submit" name="submit" value="Search"/></td>
						<td><input type="radio" name="searchType" value="city" checked>City</td>

					</form>
				</tr>
			</table>
		</div>
		<br>
		<div id="LinkContent">
			<div id="LinkName">Favorites</div>
			<div id="ContentBody">
				<div>
					<button>
						<a href="/lab03/Favorites"><type= "button">Click to
							view Favorites</a>
					</button>
				</div>
			</div>
		</div>
		<br>
		<div id="LinkContent">
			<div id="LinkName">Your Account</div>
			<div>
				<form action="${pageContext.servletContext.contextPath}/Account"
						method="post">
						<table>
							<tr>
								<td><input type="Submit" name="submit" value="Click to view Account Info"/></td>
							</tr>
						</table>
				</form>
			</div>
		</div>
	<c:if test="${! empty utype}">
	<br>
		<div id="LinkContent">
			<div id="LinkName">Your Restaurants</div>
				<div>
					<form action="${pageContext.servletContext.contextPath}/OwnerPage"
							method="get">
							<table>
								<tr>
								<td><input type="Submit" name="submit" value="Click to view Your Restaurants"/></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<c:if test="${! empty rest}">
		<div id="results">Search Results</div>
		<div id="Restaurants">
			<c:forEach items="${rest}" var="restaurant">
				<div id="LinkContent">
					<div id="LinkName">${restaurant.name}</div>
					<table>
						<tr>
							<td class = "Address">${restaurant.address },</td>
							<td class = "Address">${restaurant.city },</td>
							<td class = "Address">${restaurant.zipCode }</td>
							<form action="${pageContext.servletContext.contextPath}/${restaurant.name }" method="get">
								<td><input type="submit" value="Click to view page"></td>
							</form>
						</tr>
					</table>
				</div>
			</c:forEach>
		</div>
	</c:if>

	<c:if test="${ empty rest}">
		<div id="results">Make America Fat Again</div>
	</c:if>
	<div class="fixed">
		<button>
			<a href="/lab03/Login"><type="button">Logout </a>
		</button>
	</div>
</body>
</html>