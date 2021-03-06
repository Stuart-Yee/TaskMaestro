<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "t" tagdir = "/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


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
							<th>Owner</th>
							<td>${Task.owner.name}</td>
							<form action="/tasks/${Task.id}/reassign" method="post">
								<select name="newOwner">
									<c:forEach items="${notHelpers}" var="notHelper">
										<option value="${notHelper.id}">${notHelper.name}</option>
									</c:forEach>
								</select>
								<button>Reassign</button>
							</form>
						</tr>
						<tr>
				<th>Due Date</th><td>${Task.formattedDueDate }</td>
			</tr>
		</table>
	</td>
	<td>
<table>
		<tr><td>Helpers (${helpers.size()}):</td></tr>
		<tr>
			<td>
			<c:choose>
				<c:when test="${helpers.size() < 1 }"></c:when>
				<c:when test="${helpers.size() == 1}">${helpers.get(0).name}</c:when>
				<c:when test="${helpers.size() == 2}">${helpers.get(0).name} and ${helpers.get(1).name}</c:when>
				<c:otherwise>
					<c:forEach items="${helpers}" var="helper">
					<c:choose>
					<c:when test="${helper.id != helpers.get(helpers.size()-2).id && helper.id != helpers.get(helpers.size()-1).id}">
					${helper.name}, 
					</c:when>
					<c:when test="${helper.id == helpers.get(helpers.size()-2).id}">
					 ${helper.name} and 
					</c:when>
					<c:otherwise>
					${helper.name}
					</c:otherwise>
				</c:choose>
			</c:forEach>
				</c:otherwise>
				
			</c:choose>				
			
			<form action="/tasks/${Task.id}/addHelper" method="post">
			<select name="newHelper">
				<c:forEach items="${notHelpers}" var="notHelper">
					<option value="${notHelper.id}">${notHelper.name}</option>
				</c:forEach>
			</select>
			<button>Add Helper</button>
			</form>
			<form action="/tasks/${Task.id}/removeHelper" method="post">
			<select name="helper">
				<c:forEach items="${helpers}" var="helper">
					<option value="${helper.id}">${helper.name}</option>
				</c:forEach>
			</select>
			<button>Remove Helper</button> 
			</form>
			
			</td>
		</tr>
		<tr>
			<td>
				<c:choose>
					<c:when test="${Task.completed == true }">
						<p>Completed on ${Task.formattedCompletedDate}</p>
					</c:when>
					<c:otherwise>
						<form action="/tasks/${Task.id}/complete" method="post">
							<button>Mark Complete</button>
						</form>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</table>
	</td>
	</tr>
</table>
<p>
Created by ${Task.creator.name } on ${Task.formattedCreatedDate }
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
				<p>From: ${comment.commenter.name}, on ${comment.formattedCreatedDate}. ${comment.numberOfLikes} Likes</p>
				
				<c:if test="${fn:contains(comment.likers, user)}">					
            		<form action="/tasks/${Task.id}/comment/${comment.id}/unlike" method="post">
							<button>Unlike</button>
					</form>
        		</c:if>
        		
        		<c:if test="${not fn:contains(comment.likers, user)}">					
            		<form action="/tasks/${Task.id}/comment/${comment.id}/like" method="post">
							<button>Like</button>
					</form>
        		</c:if>
				
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
