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
			<b><spring:message code="prediction.moment"/>:</b>
			<jstl:out value="${prediction.moment}" />
		</li>
		
		<li>
			<b><spring:message code="prediction.place"/>:</b>
			<jstl:out value="${prediction.place}"/>
		</li>
		
		<li>
			<b><spring:message code="prediction.precipitation"/>:</b>
			<jstl:out value="${prediction.precipitation}"/>
		</li>
	</ul>	
</div>

<li>
<jstl:if test="${isOwner==true}">
	<form:form method="post" action="prediction/wateringArea/delete.do" modelAttribute="prediction" >
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="moment" />
		<form:hidden path="place" />
		<form:hidden path="precipitation" />
		<form:hidden path="wateringArea" />
		
		
		<jstl:if test="${prediction.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="prediction.delete" />"
				onclick="return confirm('<spring:message code="prediction.confirm.delete" />')" />&nbsp;
		</jstl:if>
	</form:form>
</jstl:if>

<acme:button code="prediction.back" url="prediction/wateringArea/list.do"/>

</li>