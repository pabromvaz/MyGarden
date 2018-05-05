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
			<b><spring:message code="messageEmail.moment"/>:</b>
			<jstl:out value="${messageEmail.moment}" />
		</li>
		
		<li>
			<b><spring:message code="messageEmail.subject"/>:</b>
			<jstl:out value="${messageEmail.subject}"/>
		</li>
		
		<li>
			<b><spring:message code="messageEmail.text"/>:</b>
			<jstl:out value="${messageEmail.text}"/>
		</li>
		
		<li>
			<b><spring:message code="messageEmail.sender" />:</b>
			<jstl:out value="${messageEmail.sender.name}" />
		</li>
		
		<li>
			<b><spring:message code="messageEmail.recipient" />:</b>
			<jstl:out value="${messageEmail.recipient.name}" />
		</li>
		
	</ul>
	
</div>

<jstl:if test="${isRecipient==true}">
	<div>
		<acme:button url="messageEmail/reply.do?messageEmailId=${messageEmail.id}" code="messageEmail.reply"/>
	</div>
</jstl:if>

<jstl:if test="${canBeArchived==true}">
<form:form method="post" action="messageEmail/archive.do" modelAttribute="messageEmail" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="subject" />
	<form:hidden path="text" />
	<form:hidden path="deletedForSender" />
	<form:hidden path="deletedForRecipient" />
	<form:hidden path="archivedForRecipient" />
	<form:hidden path="archivedForSender" />
	<form:hidden path="sender" />
	<form:hidden path="recipient" />
	
	
	<jstl:if test="${messageEmail.id != 0}">
		<input type="submit" name="archive"
			value="<spring:message code="messageEmail.archive" />" />&nbsp;
	</jstl:if>
</form:form>
</jstl:if>

<form:form method="post" action="messageEmail/delete.do" modelAttribute="messageEmail" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="subject" />
	<form:hidden path="text" />
	<form:hidden path="deletedForSender" />
	<form:hidden path="deletedForRecipient" />
	<form:hidden path="archivedForRecipient" />
	<form:hidden path="archivedForSender" />
	<form:hidden path="sender" />
	<form:hidden path="recipient" />
	
	
	<jstl:if test="${messageEmail.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="messageEmail.delete" />"
			onclick="return confirm('<spring:message code="messageEmail.confirm.delete" />')" />&nbsp;
	</jstl:if>
</form:form>

