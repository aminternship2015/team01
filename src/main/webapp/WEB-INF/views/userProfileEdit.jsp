<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<!--Import Google Icon Font-->
<link href="http://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<!-- Compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/css/materialize.min.css"
	media="screen,projection" />
<link rel="stylesheet"
	href="<c:url value=" resources
	/css/styles.css" />"
	media="screen, projection" />
	<link rel="icon"
		  type="image/png"
		  href="<c:url value=" resources/img/logo.png" />" />
<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Profile Edit</title>
</head>
<body>

	<%@include file="partial/navHome.jsp"%>


<div class="container">
		<div class="row">
		<br/>
			<p align="left">
				Hello ${loadedUser.firstName } ${loadedUser.lastName }! &nbsp; <a
					href="logout">Log Out</a>
			</p><br/>
			<h5 align="center">
				<font color="green">${successfulRegistration }</font>
				<font color="red">${userAlreadyExists }</font>
			</h5><br/>
			<h3>Your actual profile data are:</h3><br/>

			<form action="changeProfile" method="POST">			
			<input type="hidden" name="role" value="ROLE_USER"/>
			<b>First Name:</b><input type="text" name="firstName" value="${loadedUser.firstName }"/><br>
			<font color="red"><form:errors path="user.firstName"/></font><br/>
			<b>Last Name:</b><input type="text" name="lastName" value="${loadedUser.lastName }"/><br>
			<font color="red"><form:errors path="user.lastName"/></font><br/>
			<input type="hidden" name="email" value="${loadedUser.email}"/><br>			
			<b>Password:</b><input type="password" name="password" value="${loadedUser.password}"/><br>
			<font color="red"><form:errors path="user.password"/></font><br/>
			<button class="btn waves-effect waves-ligh cyan lighten-3t"
											id="Update My Profile-btn" type="submit" value="Update My Profile">Update My Profile</button>
			<!-- <input type="submit" value="Update My Profile"/> -->						
			</form>
		</div>
	</div>
	
	<%@include file="partial/footer.jsp"%>

	<!--Import jQuery before materialize.js-->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<!-- Compiled and minified JavaScript -->
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/js/materialize.min.js"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/scripts/scripts.js" />"></script>



</body>
</html>
