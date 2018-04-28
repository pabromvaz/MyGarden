<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<div>
	<ul>
		<jstl:if test="${account=='gardener'}">
		<li>
			<b><spring:message code="profile.picture" />:</b><br/>
			<img src="${profile.picture}" style = "max-width: 200 px; max-height: 200px;"/>
		</li>
		</jstl:if>	
		<li>
			<b><spring:message code="profile.name"/>:</b>
			<jstl:out value="${profile.name}" />
		</li>
		
		<li>
			<b><spring:message code="profile.surname"/>:</b>
			<jstl:out value="${profile.surname}"/>
		</li>
		
		<li>
			<b><spring:message code="profile.email"/>:</b>
			<jstl:out value="${profile.email}"/>
		</li>
	</ul>
</div>

<security:authorize access="hasAnyRole('GARDENER','ADMIN')">
	<jstl:if test="${sameActor==false}">
	<div>
		<acme:button code="messageEmail.create" url="messageEmail/create.do?actorId=${profile.id}"/>
	</div>
	</jstl:if>
</security:authorize>

<security:authorize access="hasAnyRole('ADMIN')">
	<jstl:if test="${sameActor==true}">
	<div>
		<acme:button code="profile.edit" url="administrator/edit.do"/>
	</div>
	</jstl:if>
</security:authorize>

<security:authorize access="hasAnyRole('GARDENER')">
	<jstl:if test="${sameActor==true}">
	<div>
		<acme:button code="profile.edit" url="gardener/edit.do"/>
	</div>
	</jstl:if>
</security:authorize>
