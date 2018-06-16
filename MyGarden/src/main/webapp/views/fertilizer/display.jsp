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
			<b><spring:message code="fertilizer.picture" />:</b><br/>
			<img src="${fertilizer.picture}" style = "max-width: 200 px; max-height: 200px;"/>
		</li>
		
		<li>
			<b><spring:message code="fertilizer.description"/>:</b>
			<jstl:out value="${fertilizer.description}"/>
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
		
		<li>
			<jstl:set var="isEmpty" value="${false}"/>
			<jstl:if test="${fertilizer.plants.size() == 0}">
				<jstl:set var="isEmpty" value="${true}"/>
					<spring:message code="fertilizer.noPlants" />
			</jstl:if>
			<jstl:if test="${isEmpty == false}">
			<b><spring:message code="fertilizer.plants" />:</b>
			<display:table name="${fertilizer.plants}" id="plant" class="displaytag" pagesize="5" keepStatus="true" requestURI="fertilizer/display.do?fertilizerId=${fertilizer.id}" >
					
				<acme:column code="fertilizer.plant.name" property="name" />
				<acme:column code="fertilizer.plant.description" property="description"/>
				<acme:column code="fertilizer.plant.minTemperature" property="minTemperature"/>
				<acme:column code="fertilizer.plant.maxTemperature" property="maxTemperature"/>
				<acme:column code="fertilizer.plant.moisture" property="moisture"/>
				<acme:column code="fertilizer.plant.humidity" property="humidity"/>
				
				
				<display:column>
					<a href="plant/display.do?plantId=${plant.id}"><spring:message code="fertilizer.plant.display"/></a>
				</display:column>
	
			</display:table>	
			</jstl:if>
		</li>
	</ul>
	
</div>

<security:authorize access="hasAnyRole('ADMIN')">
<form:form method="post" action="fertilizer/administrator/delete.do" modelAttribute="fertilizer" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="name" />
	<form:hidden path="picture" />
	<form:hidden path="plants" />
	<form:hidden path="description" />
	<form:hidden path="nitrogen" />
	<form:hidden path="phosphorus" />
	<form:hidden path="potassium" />
	
	
	<jstl:if test="${fertilizer.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="fertilizer.delete" />"
			onclick="return confirm('<spring:message code="fertilizer.confirm.delete" />')" />&nbsp;
	</jstl:if>
</form:form>


	<div>
		<acme:button url="fertilizer/administrator/edit.do?fertilizerId=${fertilizer.id}" code="fertilizer.edit"/>
	</div>
</security:authorize>

<acme:button code="fertilizer.back" url="fertilizer/list.do"/>