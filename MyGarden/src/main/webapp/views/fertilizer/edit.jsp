<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="fertilizer" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="plants"/>

	<acme:input code="fertilizer.name" path="name" />
	<acme:input code="fertilizer.picture" path="picture" />
	<acme:textarea code="fertilizer.description" path="description" />
	<acme:input code="fertilizer.ph" path="ph" />
	<acme:input code="fertilizer.nitrogen" path="nitrogen" />
	<acme:input code="fertilizer.phosphorus" path="phosphorus" />
	<acme:input code="fertilizer.potassium" path="potassium" />
	
	
	<acme:submit name="save" code="fertilizer.save" />
	
</form:form>