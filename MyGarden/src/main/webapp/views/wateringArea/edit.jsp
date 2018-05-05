<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="wateringArea" >
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="valveActivated"/>
	<form:hidden path="gardener"/>
	<form:hidden path="timeTables"/>
	<form:hidden path="measurements"/>
	<form:hidden path="events"/>
	<form:hidden path="tastes"/>
	<form:hidden path="comments"/>
	<form:hidden path="predictions"/>

	<acme:select items="${plants}" itemLabel="name" code="wateringArea.plant" path="plant" /> 
	
	<acme:input code="wateringArea.name" path="name" />
	<acme:input code="wateringArea.place" path="place" />
	<acme:textarea code="wateringArea.description" path="description" />
	<acme:input code="wateringArea.picture" path="picture" />
	<br/>
		<form:checkbox path="visible"/>
		<form:label path="visible">
			<spring:message code="wateringArea.visible" />
		</form:label>
		<br/>
	
	
		<acme:submit name="save" code="wateringArea.save" />
		
	<jstl:if test="${comment.id == 0}">
		<acme:cancel url="wateringArea/list.do" code="wateringArea.cancel" />
	</jstl:if>
	<jstl:if test="${comment.id != 0}">
		<acme:cancel url="wateringArea/display.do?wateringAreaId=${wateringArea.id}" code="wateringArea.cancel" />
	</jstl:if>
</form:form>

