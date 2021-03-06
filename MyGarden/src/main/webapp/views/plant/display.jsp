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
			<b><spring:message code="plant.name"/>:</b>
			<jstl:out value="${plant.name}" />
		</li>
		
		
			<!-- <b><spring:message code="plant.picture" />:</b><br/>-->
			<img src="${plant.picture}" style = "max-width: 200 px; max-height: 200px;"/>
		
		
		<li>
			<b><spring:message code="plant.description"/>:</b>
			<jstl:out value="${plant.description}"/>
		</li>
		
		<li>
			<b><spring:message code="plant.minTemperature"/>:</b>
			<jstl:out value="${plant.minTemperature} �C"/>
		</li>
		
		<li>
			<b><spring:message code="plant.maxTemperature" />:</b>
			<jstl:out value="${plant.maxTemperature} �C" />
		</li>
		
		<li>
			<b><spring:message code="plant.moisture" />:</b>
			<jstl:out value="${plant.moisture} %" />
		</li>
		
		<li>
			<b><spring:message code="plant.ph" />:</b>
			<jstl:out value="${plant.ph}" />
		</li>
		
		<li>
			<b><spring:message code="plant.humidity" />:</b>
			<jstl:out value="${plant.humidity} %" />
		</li>
		
		<jstl:set var="isEmpty" value="${false}"/>
			<jstl:if test="${plant.wateringAreas.size() == 0}">
				<li>
			
					<jstl:set var="isEmpty" value="${true}"/>
					<spring:message code="plant.noWateringAreas" />
			
				</li>
		</jstl:if>
	</ul>
	
</div>




<security:authorize access="hasAnyRole('ADMIN')">
<form:form method="post" action="plant/administrator/delete.do" modelAttribute="plant" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="name" />
	<form:hidden path="picture" />
	<form:hidden path="description" />
	<form:hidden path="minTemperature" />
	<form:hidden path="maxTemperature" />
	<form:hidden path="moisture" />
	<form:hidden path="ph" />
	<form:hidden path="wateringAreas" />
	<form:hidden path="fertilizers" />
	
	<jstl:if test="${plant.id != 0 && isEmpty}">
		<input type="submit" name="delete"
			value="<spring:message code="plant.delete" />"
			onclick="return confirm('<spring:message code="plant.confirm.delete" />')" />&nbsp;
	</jstl:if>
</form:form>
</security:authorize>

<security:authorize access="hasAnyRole('ADMIN')">
	<div>
		<acme:button url="plant/administrator/edit.do?plantId=${plant.id}" code="plant.edit"/>
	</div>
</security:authorize>

<acme:button code="plant.back" url="plant/list.do"/>

