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
			<b><spring:message code="fertilizer.name"/>:</b>
			<jstl:out value="${fertilizer.name}" />
		</li>
		
		<li>
			<b><spring:message code="fertilizer.description"/>:</b>
			<jstl:out value="${fertilizer.description}"/>
		</li>
		
		<li>
			<b><spring:message code="fertilizer.ph"/>:</b>
			<jstl:out value="${fertilizer.ph}"/>
		</li>
		
		<li>
			<b><spring:message code="fertilizer.nitrogen" />:</b>
			<jstl:out value="${fertilizer.nitrogen}" />
		</li>
		
		<li>
			<b><spring:message code="fertilizer.phosphorus" />:</b>
			<jstl:out value="${fertilizer.phosphorus}" />
		</li>
		
		<li>
			<b><spring:message code="fertilizer.potassium" />:</b>
			<jstl:out value="${fertilizer.potassium}" />
		</li>
	</ul>
	
</div>


<form:form method="post" action="fertilizer/administrator/delete.do" modelAttribute="fertilizer" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="name" />
	<form:hidden path="description" />
	<form:hidden path="ph" />
	<form:hidden path="nitrogen" />
	<form:hidden path="phosphorus" />
	<form:hidden path="potassium" />
	
	
	<jstl:if test="${fertilizer.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="fertilizer.delete" />"
			onclick="return confirm('<spring:message code="fertilizer.confirm.delete" />')" />&nbsp;
	</jstl:if>
</form:form>

<security:authorize access="hasAnyRole('ADMIN')">
	<div>
		<acme:button url="fertilizer/administrator/edit.do?fertilizerId=${fertilizer.id}" code="fertilizer.edit"/>
	</div>
</security:authorize>