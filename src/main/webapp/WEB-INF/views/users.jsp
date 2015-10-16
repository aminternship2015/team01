<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<!--Import Google Icon Font-->
	<link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<!-- Compiled and minified CSS -->
	<link rel="stylesheet"
		  href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/css/materialize.min.css"
		  media="screen,projection"/>
	<link rel="stylesheet" href="<c:url value=" resources
	/css/styles.css" />" media="screen, projection" />
	<!--Let browser know website is optimized for mobile-->
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Users page</title>
</head>
<body>

<%@include file="partial/nav.jsp" %>


	<h3>Users List</h3>
	<c:if test="${!empty userList}">
		<table class="tg" border="1">
			<tr>
				<th width="80">First Name</th>
				<th width="120">Last Name</th>
				<th width="120">Email</th>
			</tr>
			<c:forEach items="${userList}" var="person">
				<tr>
					<td>${person.firstName}</td>
					<td>${person.lastName}</td>
					<td><a href="${person.email}"></a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

<%@include file="partial/footer.jsp" %>


<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<!-- Compiled and minified JavaScript -->
<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/js/materialize.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/scripts.js" />"></script>

</body>
</html>