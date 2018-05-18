<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<div class="table-responsive">
<display:table name="messageEmails" id="messageEmail" requestURI="messageEmail/list.do" class="displaytag">
	

	
	<acme:column code="messageEmail.subject" property="subject"/>
	<acme:column code="messageEmail.text" property="text"/>
	<acme:column code="messageEmail.moment" property="moment"/>
	<acme:column code="messageEmail.sender" property="sender.name"/>
	<acme:column code="messageEmail.recipient" property="recipient.name"/>
	<security:authorize access="hasAnyRole('ADMIN','GARDENER')">
	<display:column>
		<a href="messageEmail/display.do?messageEmailId=${messageEmail.id}"><spring:message
		code="messageEmail.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>
</div>



