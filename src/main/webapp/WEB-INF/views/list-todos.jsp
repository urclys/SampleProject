<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="cummon/header.jspf" %>
<%@include file="cummon/navigation.jspf" %>
	<div class="container">
		<h2>Hi ${name}</h2>
		<table class="table table-striped">
			<caption>Your Todos are</caption>
			<thead>
				<tr>
					<th>Description</th>
					<th>Target Date</th>
					<th>is Completed ?</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.desc}</td>
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${todo.targetDate}"></fmt:formatDate></td>
						<td>${todo.done}</td>
						<td><a class="btn btn-warning" href="/update-todo?id=${todo.id}">Edit</a>
						<a class="btn btn-danger" href="/delete-todo?id=${todo.id}">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div>
			<a class="btn btn-success" href="/add-todo">Add</a>
		</div>
	</div>
<%@include file="cummon/footer.jspf" %>