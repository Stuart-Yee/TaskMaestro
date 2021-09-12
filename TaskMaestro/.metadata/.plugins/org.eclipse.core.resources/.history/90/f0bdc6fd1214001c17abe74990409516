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

Yo, <c:out value="${user.name }"/>!

	<table class="table">
		<thead>
			<tr>
				<th>Task</th><th>Owner</th><th>Due Date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${openTasks}" var="task">
				<tr>
					<td><a href="/tasks/${task.id}/view">${task.description}</a></td><td>${task.owner.name}</td><td>${task.dueDate}</td>
				</tr>
			</c:forEach>			
		</tbody>

	</table>

</t:nav>
</body>
</html>