<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form method="post" action="messageEmail/create.do" modelAttribute="messageEmail" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	<form:hidden path="deletedForSender" />
	<form:hidden path="deletedForRecipient" />
	<form:hidden path="archivedForSender" />
	<form:hidden path="archivedForRecipient" />
	<form:hidden path="recipient" />

	<%-- <acme:select items="${recipients}" itemLabel="name" code="messageEmail.recipient" path="recipient" /> --%>
	
	<acme:input code="messageEmail.subject" path="subject" />
	
	<acme:textarea code="messageEmail.text" path="text" />
	
	
	<acme:submit name="save" code="messageEmail.save" />
	
</form:form>