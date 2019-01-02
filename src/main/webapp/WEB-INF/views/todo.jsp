<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="cummon/header.jspf" %>
<%@include file="cummon/navigation.jspf" %>
	<div class="container">
	<H2>Add Todo </H2>
		
		<form:form method="post" commandName="todo">
		<form:hidden path="id"/>
		
			<fieldset class="form-group">
				<form:label path="desc" for="descriptionInput">Description</form:label> 
				<form:input path="desc" class="form-control" type="text" placeholder="My Todo" id="descriptionInput" required="required"/>
				<form:errors path="desc" cssClass="text-warning"/>
			</fieldset>
			<fieldset class="form-group">
				<form:label path="targetDate" for="descriptionInput">Target Date</form:label> 
				<form:input path="targetDate" class="form-control" type="text" placeholder="My target Date" id="descriptionInput" required="required"/>
				<form:errors path="targetDate" cssClass="text-warning"/>
			</fieldset>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form:form>
	</div>
<%@include file="cummon/footer.jspf" %>