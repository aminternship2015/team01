<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

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
    <link rel="icon"
          type="image/png"
          href="<c:url value=" resources/img/logo.png" />" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Sing Up Login Page</title>
</head>
<body>

<%@include file="partial/navRest.jsp" %>
<div class="container">
<h3 align="center">REST WEB SERVICES!</h3>
<br/>
<!-- <p><a href="userapi"><button class="btn waves-effect waves-ligh cyan lighten-3t">View all users</button></a></p> -->
<br/>

<form action="userapi" method="POST">
ID: <input type="text" name="id"  /><br/>
First Name: <input type="text" name="firstName" /><br/>
Last Name:<input type="text" name="lastName"  /><br/>
Email:<input type="text" name="email"/><br/>
Password:<input type="password" name="password" /><br/>
<button class="btn waves-effect waves-ligh cyan lighten-3t" type="submit" name="action" value="create">Create User</button><br/><br/>
<button class="btn waves-effect waves-ligh cyan lighten-3t" type="submit" name="action" value="update">Update User</button><br/><br/>
<button class="btn waves-effect waves-ligh cyan lighten-3t" type="submit" name="action" value="delete">Delete User</button><br/><br/>
<button class="btn waves-effect waves-ligh cyan lighten-3t get" type="submit" name="action" value="deleteid">Get User By Id</button><br/><br/>
<button class="btn waves-effect waves-ligh cyan lighten-3t" type="reset" >Reset Form</button>
</form><p><a href="userapi"><button class="btn waves-effect waves-ligh cyan lighten-3t">View all users</button></a></p>

<br/><br/><br/>

<%-- <form action="userapi" method="POST">
ID: <input type="text" name="id"/><br/>
First Name: <input type="text" name="firstName" /><br/>
Last Name:<input type="text" name="lastName"  /><br/>
Password:<input type="password" name="password" /><br/>
<button class="btn waves-effect waves-ligh cyan lighten-3t" type="submit" name="update">Update User</button>
</form> --%>


</div>
<!-- <br/><br/><br/> -->
<%@include file="partial/footer.jsp" %>


<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<!-- Compiled and minified JavaScript -->
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/js/materialize.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/scripts/scripts.js" />"></script>
</body>
</html>