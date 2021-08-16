<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "t" tagdir = "/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/css/stylesheet.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<title>TaskMaestro Dashboard</title>
</head>
<body>
<t:nav>

<form action="/tasks/new" method="post">
	<textarea class="form-control" required autofocus name="description">Task Description...</textarea>
	<label for="dueDate">Due Date:</label>
	<input type="date" class="form-control" autofocus name="dueDate">
	<label for="owner">Owner:</label>
	<select class="form-control" autofocus name="owner">
		<c:forEach items="${users}" var="owner">
			<c:choose>
				<c:when test="${user.id == owner.id }">
					<option selected value="${user.id}">${user.name} -- ME! </option>
				</c:when>
				<c:otherwise>
					<option value="${owner.id}">${owner.name}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>	
	</select>
	<button class="btn btn-outline-success">Save!</button>
</form>
	

</t:nav>
</body>
</html>
