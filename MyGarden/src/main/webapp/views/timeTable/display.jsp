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
			<b><spring:message code="timeTable.activationMoment"/>:</b>
			<jstl:out value="${timeTable.activationMoment}" />
		</li>
		
		<li>
			<b><spring:message code="timeTable.deactivationMoment"/>:</b>
			<jstl:out value="${timeTable.deactivationMoment}"/>
		</li>
	</ul>
	<jstl:if test="${isOwner==true}">
	<div>
		<acme:button url="timeTable/wateringArea/edit.do?timeTableId=${timeTable.id}" code="timeTable.edit"/>
	</div>
	</jstl:if>
	
	<acme:button code="timeTable.back" url="timeTable/wateringArea/list.do?wateringArea=${timeTable.wateringArea.id}"/>
</div>



<jstl:if test="${isOwner==true}">
	<form:form method="post" action="timeTable/wateringArea/delete.do" modelAttribute="timeTable" >
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="activationMoment" />
		<form:hidden path="deactivationMoment" />
		<form:hidden path="wateringArea" />
		
		<jstl:if test="${timeTable.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="timeTable.delete" />"
				onclick="return confirm('<spring:message code="timeTable.confirm.delete" />')" />&nbsp;
		</jstl:if>
	</form:form>
</jstl:if>
