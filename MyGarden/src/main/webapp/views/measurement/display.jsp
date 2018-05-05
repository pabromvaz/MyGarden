<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

<div>
	<ul>
		<li>
			<b><spring:message code="measurement.moisture"/>:</b>
			<jstl:out value="${measurement.moisture}" />
		</li>
		
		<li>
			<b><spring:message code="measurement.humidity"/>:</b>
			<jstl:out value="${measurement.humidity}"/>
		</li>
		
		<li>
			<b><spring:message code="measurement.temperature"/>:</b>
			<jstl:out value="${measurement.temperature}"/>
		</li>
		
		<li>
			<b><spring:message code="measurement.light" />:</b>
			<jstl:out value="${measurement.light}" />
		</li>
		
		<li>
			<b><spring:message code="measurement.ph" />:</b>
			<jstl:out value="${measurement.ph}" />
		</li>
		
		<li>
			<b><spring:message code="measurement.nitrogen" />:</b>
			<jstl:out value="${measurement.nitrogen}" />
		</li>
		
		<li>
			<b><spring:message code="measurement.phosphorus" />:</b>
			<jstl:out value="${measurement.phosphorus}" />
		</li>
		
		<li>
			<b><spring:message code="measurement.potassium" />:</b>
			<jstl:out value="${measurement.potassium}" />
		</li>
		
		<li>
			<b><spring:message code="measurement.moment" />:</b>
			<jstl:out value="${measurement.moment}" />
		</li>
	</ul>
	
</div>

<jstl:if test="${isOwner==true}">
	<form:form method="post" action="measurement/wateringArea/delete.do" modelAttribute="measurement" >
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moisture" />
		<form:hidden path="humidity" />
		<form:hidden path="temperature" />
		<form:hidden path="light" />
		<form:hidden path="ph" />
		<form:hidden path="nitrogen" />
		<form:hidden path="phosphorus" />
		<form:hidden path="potassium" />
		<form:hidden path="moment" />
		<form:hidden path="wateringArea" />
		
		
		<jstl:if test="${measurement.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="measurement.delete" />"
				onclick="return confirm('<spring:message code="measurement.confirm.delete" />')" />&nbsp;
		</jstl:if>
	</form:form>
</jstl:if>

<acme:button code="measurement.back" url="measurement/wateringArea/list.do?wateringAreaId=${measurement.wateringArea.id}"/>