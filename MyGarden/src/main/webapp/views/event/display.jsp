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
			<b><spring:message code="event.name"/>:</b>
			<jstl:out value="${event.name}" />
		</li>
		
		<li>
			<b><spring:message code="event.description"/>:</b>
			<jstl:out value="${event.description}"/>
		</li>
		
		<li>
			<b><spring:message code="event.moment"/>:</b>
			<jstl:out value="${event.moment}"/>
		</li>		
	</ul>
	
</div>

<jstl:if test="${isOwner==true}">
	<form:form method="post" action="event/wateringArea/delete.do" modelAttribute="event" >

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="name" />
		<form:hidden path="description" />
		<form:hidden path="moment" />
		<form:hidden path="wateringArea" />
		
	
	
		<jstl:if test="${event.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="event.delete" />"
				onclick="return confirm('<spring:message code="event.confirm.delete" />')" />&nbsp;
		</jstl:if>
</form:form>
</jstl:if>

<acme:button code="event.back" url="event/list.do?wateringAreaId=${event.wateringArea.id}"/>