<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</p>
	<p>File Contents: ${fileContents}</p>

	<table>
		<c:forEach items="${contactList}" var="contact">
			<tr>
				<td>${contact.id}</td>
				<td>${contact.name}</td>
				<td>${contact.email}</td>
				<td>${contact.address}</td>
				<td>${contact.telephone}</td>
			</tr>
		</c:forEach>
	</table>

	<%-- 	<p>${myContact.name} ${myContact.telephone} ${myContact.email}</p> --%>
	<p>${myContact}</p>

	<form action="user/get" method="post">
		<input type="text" name="username"><br /> <input
			type="submit" value="Login">
	</form>
</body>
</html>
