<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="plant" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="wateringAreas" />

	<acme:input code="plant.name" path="name" />
	<acme:textarea code="plant.description" path="description" />
	<acme:input code="plant.minTemperature" path="minTemperature" />
	<acme:input code="plant.maxTemperature" path="maxTemperature" />
	<acme:input code="plant.moisture" path="moisture" />
	<acme:input code="plant.ph" path="ph" />
	
	
	<acme:submit name="save" code="plant.save" />
	
</form:form>