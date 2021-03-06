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
<title>TaskMaestro: ${Task.description}</title>
</head>
<body>
<t:nav>

<h1>${Task.description}</h1>
<table class="table">
	<tr><td>
		<table>
			<tr>
				<th>Owner</th><td>${Task.owner.name }</td>
			</tr>
			<tr>
				<th>Due Date</th><td>${Task.dueDate }</td>
			</tr>
		</table>
	</td>
	<td>
		<table>
		<tr><td>Helpers:</td></tr>
		<tr>
			<td>
			List of helpers and function to add helpers
			
			</td>
		</tr>
		</table>
	</td>
	</tr>
</table>
<p>
Created by ${Task.creator.name } on ${Task.createdAt }
</p>
<h2>Comments</h2>
<form action="/tasks/${Task.id}/comment" method="post">
	<textarea name="comment"></textarea>
	<button>Leave Comment</button>
</form>

<table class="table table-bordered">
	<c:forEach items="${comments}" var = "comment">
		<tr>
			<td>
				<p>${comment.content}</p>
				<p>From: ${comment.commenter.name}, on ${comment.createdAt}. ${comment.numberOfLikes} Likes</p>
				<c:choose>
					<c:when test="${comment.commenter.id == user.id }">
						<p><a href="/tasks/${Task.id }/delete/${comment.id}">Delete</a></p>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
			</td>
			<td>Liked By:
				<c:forEach items="${comment.likers }" var="liker">
					<p>${liker.name}</p>
				</c:forEach>
			</td>
		</tr>
	</c:forEach>

	
</table>
</t:nav>
</body>
</html>
