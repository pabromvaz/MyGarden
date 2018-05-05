<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="timeTable" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="wateringArea" />

	<acme:input code="timeTable.activationMoment" path="activationMoment" />
	<acme:input code="timeTable.deactivationMoment" path="deactivationMoment" />
		
	<acme:submit name="save" code="timeTable.save" />
	
	<jstl:if test="${timeTable.id == 0}">
		<acme:cancel url="timeTable/wateringArea/list.do?wateringAreaId=${timeTable.wateringArea.id}" code="timeTable.cancel" />
	</jstl:if>
	<jstl:if test="${timeTable.id != 0}">
		<acme:cancel url="timeTable/wateringArea/display.do?wateringAreaId=${timeTable.wateringArea.id}" code="timeTable.cancel" />
	</jstl:if>
</form:form>