<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!-- 
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
-->
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="createGardenerForm">
	<acme:input code="gardener.username" path="username" />
	<acme:password code="gardener.password" path="password" />
	<acme:password code="gardener.confirmPassword" path="confirmPassword" />
	<acme:input code="gardener.name" path="name" />
	<acme:input code="gardener.surname" path="surname" />
	<acme:input code="gardener.email" path="email" />
	<acme:input code="gardener.picture" path="picture" />
	
	<jstl:if test="${requestURI == 'gardener/create.do'}">
		<br/>
		<form:checkbox path="isAgree"/>
		<form:label path="isAgree">
			<spring:message code="gardener.isAgree" />
			<a href="misc/conditions.do" target="_blank"><spring:message code="gardener.conditions" /></a>
		</form:label>
		<br/>
	</jstl:if>
	
	<acme:submit name="save" code="gardener.save" />
	<acme:cancel url="" code="gardener.cancel" />
</form:form>

<!-- 
 <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
-->