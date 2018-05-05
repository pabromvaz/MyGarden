<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="configuration" >
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="events"/>
	<form:hidden path="manualWatering"/>
	<form:hidden path="automaticWatering"/>

	<acme:input code="configuration.intrusionWarningActivated" path="intrusionWarningActivated" />
	<acme:input code="configuration.fertilizerWarningActivated" path="fertilizerWarningActivated" />
	<acme:textarea code="configuration.tankWarningActivated" path="tankWarningActivated" />
	
	
	<acme:submit name="save" code="configuration.save" />
	
</form:form>